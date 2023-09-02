package de.placeblock.unuserver.game.round;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.placeblock.unuserver.Main;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.DrawStackApplier;
import de.placeblock.unuserver.cards.impl.NumberCard;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.player.Inventory;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Round {
    @JsonIgnore
    private final Room room;
    private final List<RoundPlayer> players = new ArrayList<>();
    private final RoundSettings roundSettings;
    private RoundPlayer currentPlayer;
    // Players who have to shout "UNO!"
    private final List<RoundPlayer> acknowledgeLastCardPlayers = new ArrayList<>();
    private final LinkedList<Card<?>> cardStack = new LinkedList<>();
    private final LinkedList<Card<?>> placedCards = new LinkedList<>();
    private int placedCardsAll = 0;
    private int drawStack = 0;
    @Setter
    private int nextPlayerDelta = 1;
    private boolean hasPlayerDrawnCard = false;

    public Round(Room room, RoundSettings roundSettings, List<Player> players, List<Card<?>> cardStack) {
        Main.LOGGER.info("Creating Round for Room " + room.getCode());
        this.room = room;
        this.roundSettings = roundSettings;
        for (Player player : players) {
            RoundPlayer roundPlayer = new RoundPlayer(player);
            Inventory inventory = new Inventory(roundPlayer);
            roundPlayer.setInventory(inventory);
            this.players.add(roundPlayer);
        }
        int randomPlayerIndex = (int) (Math.random() * this.players.size());
        this.currentPlayer = this.players.get(randomPlayerIndex);
        Collections.shuffle(cardStack);
        this.cardStack.addAll(cardStack);
        this.placedCards.add(this.getBeginningCard());
        for (RoundPlayer roundPlayer : this.players) {
            List<Card<?>> cards = this.drawCards(roundSettings.getStartCardAmount());
            roundPlayer.getInventory().addCards(cards);
        }
        RoundData roundData = RoundData.fromRound(this);
        for (RoundPlayer roundPlayer : this.players) {
            Player player = roundPlayer.getPlayer();
            player.setRoundData(roundData);
            player.setInventory(roundPlayer.getInventory());
        }
    }

    private Card<?> getBeginningCard() {
        List<Card<?>> beginCards = new ArrayList<>();
        for (Card<?> card : this.cardStack) {
            if (card.canBeginWith()) {
                beginCards.add(card);
            }
        }
        if (beginCards.size() > 0) {
            Collections.shuffle(beginCards);
            Card<?> beginCard = beginCards.get(0);
            this.cardStack.remove(beginCard);
            return beginCard;
        } else {
            int number = ThreadLocalRandom.current().nextInt(0, 10);
            int colorIndex = ThreadLocalRandom.current().nextInt(0, 4);
            Color color = Color.values()[colorIndex];
            return new NumberCard(number, color);
        }
    }

    // PLAYER MANAGEMENT

    public RoundPlayer calculateNextPlayer(int nextPlayerDelta) {
        int currentPlayerIndex = this.players.indexOf(this.currentPlayer);
        int newPlayerIndex = (currentPlayerIndex + nextPlayerDelta) % this.players.size();
        return this.players.get(newPlayerIndex);
    }

    public RoundPlayer calculateNextPlayer() {
        return this.calculateNextPlayer(this.nextPlayerDelta);
    }

    public void removePlayer(RoundPlayer roundPlayer, RemovePlayerReason reason) {
        this.players.remove(roundPlayer);
        if (this.players.size() == 1) {
            this.room.getLeaderboard().addWin(this.players.get(0).getPlayer());
            this.room.endRound();
            return;
        }
        this.room.executeForPlayers(p -> p.removeRoundPlayer(roundPlayer, reason));
        if (this.currentPlayer.equals(roundPlayer)) {
            this.setNextPlayer(this.calculateNextPlayer());
        }
    }

    public RoundPlayer getRoundPlayer(UUID uuid) {
        RoundPlayer roundPlayer = null;
        for (RoundPlayer rp : this.players) {
            if (rp.getPlayer().getUuid().equals(uuid)) {
                roundPlayer = rp;
            }
        }
        return roundPlayer;
    }

    public void setNextPlayer(RoundPlayer player) {
        this.currentPlayer = player;
        this.room.executeForPlayers(p -> p.setCurrentPlayer(this.currentPlayer));
    }

    // CARD MANAGEMENT

    public Card<?> getCurrentCard() {
        return this.placedCards.getLast();
    }

    public void placeCard(RoundPlayer roundPlayer, UUID cardUUID) {
        Card<?> card = roundPlayer.getInventory().getCard(cardUUID);
        if (card == null ||
            !this.currentPlayer.equals(roundPlayer) ||
            !this.canPlaceCard(card)) return;
        // Players that didn't say UNO before the next player places a card get punished
        this.punishNotAcknowledgedPlayers();
        this.placeCard(card);
        if (card instanceof DrawStackApplier) {
            this.applyDrawStack(roundPlayer);
        }
        roundPlayer.getPlayer().removeCard(cardUUID);
        roundPlayer.getInventory().removeCard(card);
        roundPlayer.incPlacedCards();

        if (roundPlayer.getInventory().size() == 0) {
            this.removePlayer(roundPlayer, RemovePlayerReason.WON);
        }

        this.setNextPlayer(this.calculateNextPlayer());
    }

    public void drawCard(RoundPlayer roundPlayer) {
        if (!this.currentPlayer.equals(roundPlayer) ||
            this.hasPlayerDrawnCard) return;
        this.hasPlayerDrawnCard = true;
        // Players that didn't say UNO before the next player draws a card get punished
        this.punishNotAcknowledgedPlayers();
        this.applyDrawStack(roundPlayer);
        Card<?> card = this.drawCard();
        roundPlayer.getInventory().addCard(card);
        roundPlayer.getPlayer().addCard(card, AddCardReason.DRAW);
    }

    public Card<?> drawCard() {
        Card<?> card = this.cardStack.pop();
        if (this.cardStack.isEmpty()) {
            this.refillCardStack();
        }
        return card;
    }

    public List<Card<?>> drawCards(int amount) {
        List<Card<?>> drawnCards = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            drawnCards.add(this.drawCard());
        }
        return drawnCards;
    }

    public void applyDrawStack(RoundPlayer roundPlayer) {
        if (this.drawStack == 0) return;
        List<Card<?>> cards = this.drawCards(this.drawStack);
        this.setDrawStack(0);
        for (Card<?> card : cards) {
            roundPlayer.getInventory().addCard(card);
            roundPlayer.getPlayer().addCard(card, AddCardReason.DRAW_STACK);
        }
    }

    public void setDrawStack(int drawStack) {
        this.drawStack = drawStack;
        this.room.executeForPlayers(p -> p.setDrawStack(this.drawStack));
    }

    private void refillCardStack() {
        for (int i = 0; i < this.placedCards.size() - 1; i++) {
            this.cardStack.push(this.placedCards.pop());
        }
    }

    public boolean canPlaceCard(Card<?> card) {
        return this.getCurrentCard().isValidNextCard(this, card);
    }

    public void placeCard(Card<?> card) {
        card.place(this);
        this.placedCards.add(card);
        this.room.executeForPlayers(p -> p.setPlacedCard(card));
        // Only for statistics
        this.placedCardsAll++;
    }

    public void acknowledgeLastCard(RoundPlayer player) {
        this.acknowledgeLastCardPlayers.remove(player);
        this.room.executeForPlayers(p -> p.showPlayerAcknowledgeLastCard(player));
    }

    /**
     * Gives punishment-cards to player who didn't shout "UNO!"
     */
    private void punishNotAcknowledgedPlayers() {
        for (RoundPlayer roundPlayer : this.acknowledgeLastCardPlayers) {
            List<Card<?>> drawnCards = this.drawCards(2);
            for (Card<?> drawnCard : drawnCards) {
                roundPlayer.getInventory().addCard(drawnCard);
                roundPlayer.getPlayer().addCard(drawnCard, AddCardReason.NO_LAST_CARD_ACKNOWLEDGE);
            }
        }
    }


    public enum AddCardReason {
        DRAW,
        DRAW_STACK,
        NO_LAST_CARD_ACKNOWLEDGE,
    }

    public enum RemovePlayerReason {
        LEFT,
        KICKED,
        WON
    }

    // This gets sent over network
    @Getter
    @RequiredArgsConstructor
    public static class RoundData {
        private final List<UUID> players;
        private final UUID currentPlayer;
        private final Map<UUID, Integer> playerCards;
        private final int drawStack;
        private final List<Card<?>> placedCards;

        public static RoundData fromRound(Round round) {
            Map<UUID, Integer> playerCards = new HashMap<>();
            for (RoundPlayer roundPlayer : round.players) {
                playerCards.put(roundPlayer.getPlayer().getUuid(), roundPlayer.getInventory().size());
            }
            return new RoundData(
                    round.getPlayers().stream().map(p -> p.getPlayer().getUuid()).toList(),
                    round.getCurrentPlayer().getPlayer().getUuid(),
                    playerCards,
                    round.getDrawStack(),
                    round.placedCards
            );
        }
    }
}

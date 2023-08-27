package de.placeblock.unuserver.game.round;

import de.placeblock.unuserver.Main;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.player.Inventory;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
public class Round {
    private final Room room;
    private final List<RoundPlayer> players = new ArrayList<>();
    private final RoundSettings roundSettings;
    private RoundPlayer currentPlayer;
    // Players who have to shout "UNO!"
    private final List<RoundPlayer> acknowledgeLastCardPlayers = new ArrayList<>();
    private final LinkedList<Card> cardStack = new LinkedList<>();
    private final List<Card> placedCards = new ArrayList<>();
    private int placedCardsAll = 0;
    private int drawStack = 0;
    @Setter
    private int nextPlayerDelta = 1;

    public Round(Room room, RoundSettings roundSettings, List<Player> players, List<Card> cardStack) {
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
        this.setNextPlayer(this.players.get(randomPlayerIndex));
        this.cardStack.addAll(cardStack);
        for (RoundPlayer player : this.players) {
            List<Card> cards = this.drawCards(roundSettings.getStartCardAmount());
            player.getInventory().addCards(cards);
            player.getPlayer().setInventory(player.getInventory());
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
        if (this.players.size() == 1) {
            this.room.endRound();
            return;
        }
        this.players.remove(roundPlayer);
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

    public Card getCurrentCard() {
        return this.placedCards.get(this.placedCards.size()-1);
    }

    public void placeCard(RoundPlayer roundPlayer, Card card) {
        if (!this.currentPlayer.equals(roundPlayer) ||
            !this.canPlaceCard(card) ||
            !roundPlayer.getInventory().hasCard(card)) return;
        // TODO: CHECK CURRENT MOVE STATE
        // Players that didn't say UNO before the next player places a card get punished
        this.punishNotAcknowledgedPlayers();
        this.placeCard(card);
        roundPlayer.getInventory().removeCard(card);

        if (roundPlayer.getInventory().size() == 0) {
            this.removePlayer(roundPlayer, RemovePlayerReason.WON);
        }
    }

    public void drawCard(RoundPlayer roundPlayer) {
        if (!this.currentPlayer.equals(roundPlayer)) return;
        // TODO: IMPLEMENT
        // TODO: CHECK CURRENT MOVE STATE
        // Players that didn't say UNO before the next player draws a card get punished
        this.punishNotAcknowledgedPlayers();
        this.applyDrawStack(roundPlayer);
        Card card = this.drawCard();
        roundPlayer.getInventory().addCard(card);
        roundPlayer.getPlayer().confirmDrawnCard(card, DrawReason.DRAW);
    }

    public Card drawCard() {
        Card card = this.cardStack.pop();
        if (this.cardStack.isEmpty()) {
            this.refillCardStack();
        }
        return card;
    }

    public List<Card> drawCards(int amount) {
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            drawnCards.add(this.drawCard());
        }
        return drawnCards;
    }

    public void applyDrawStack(RoundPlayer roundPlayer) {
        if (this.drawStack == 0) return;
        List<Card> cards = this.drawCards(this.drawStack);
        this.setDrawStack(0);
        for (Card card : cards) {
            roundPlayer.getInventory().addCard(card);
            roundPlayer.getPlayer().confirmDrawnCard(card, DrawReason.DRAW_STACK);
        }
    }

    public void setDrawStack(int drawStack) {
        this.drawStack = drawStack;
        this.room.executeForPlayers(p -> p.setDrawStack(this.drawStack));
    }

    private void refillCardStack() {
        List<Card> oldCards = this.placedCards.subList(0, this.placedCards.size() - 1);
        this.cardStack.addAll(oldCards);
    }

    public boolean canPlaceCard(Card card) {
        return this.getCurrentCard().isValidNextCard(card);
    }

    public void placeCard(Card card) {
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
            List<Card> drawnCards = this.drawCards(2);
            for (Card drawnCard : drawnCards) {
                roundPlayer.getInventory().addCard(drawnCard);
                roundPlayer.getPlayer().confirmDrawnCard(drawnCard, DrawReason.NO_LAST_CARD_ACKNOWLEDGE);
            }
        }
    }


    public enum DrawReason {
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

        public static RoundData fromRound(Round round) {
            Map<UUID, Integer> playerCards = new HashMap<>();
            for (RoundPlayer roundPlayer : round.players) {
                playerCards.put(roundPlayer.getPlayer().getUuid(), roundPlayer.getInventory().size());
            }
            return new RoundData(
                    round.getPlayers().stream().map(p -> p.getPlayer().getUuid()).toList(),
                    round.getCurrentPlayer().getPlayer().getUuid(),
                    playerCards,
                    round.getDrawStack()
            );
        }
    }
}

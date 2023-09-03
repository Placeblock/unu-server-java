package de.placeblock.unuserver.game;

import de.placeblock.unuserver.Main;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.CardDeck;
import de.placeblock.unuserver.cards.stackpresets.StandartCardDeck;
import de.placeblock.unuserver.communication.Chat;
import de.placeblock.unuserver.game.round.DefaultRoundSettings;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.game.round.RoundSettings;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class Room {
    private final String code;
    private final Chat chat = new Chat(this);
    private UUID owner;
    private final List<Player> players = new ArrayList<>();
    private State state = State.LOBBY;
    private final Leaderboard leaderboard = new Leaderboard(this);
    private RoundSettings roundSettings = new DefaultRoundSettings();
    private CardDeck cardDeck = new StandartCardDeck();
    private Round round;

    public void setState(State state) {
        this.state = state;
        this.executeForPlayers(p -> p.setRoomState(this.state));
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        this.executeForPlayers(p -> p.setOwner(owner));
    }

    public void startRound() {
        List<Card<?>> cards = this.cardDeck.flatten();
        if (cards.size() < this.players.size()*this.roundSettings.getStartCardAmount()*2 ||
                this.players.size() < 2) return;
        this.round = new Round(this, this.roundSettings, this.players, cards);
        this.setState(State.INGAME);
    }

    public void endRound() {
        this.round = null;
        this.setState(State.LOBBY);
    }

    public void setCardDeck(CardDeck cardDeck) {
        this.cardDeck = cardDeck;
        this.executeForPlayers(p -> p.setCardDeck(this.cardDeck));
    }

    public void setRoundSettings(RoundSettings roundSettings) {
        this.roundSettings = roundSettings;
        this.executeForPlayers(p -> p.setRoundSettings(this.roundSettings));
    }

    public void addPlayer(Player player) {
        if (this.owner == null) {
            this.owner = player.getUuid();
        }
        player.setRoom(this);
        Main.LOGGER.info("Adding Player to Room " + this.getCode());
        this.players.add(player);
        player.setCardDeckPresets();
        player.setRoomData(RoomData.fromRoom(this));
        this.executeForPlayers(player, p -> p.sendPlayerData(player));
    }

    public void removePlayer(Player player, boolean kicked) {
        Main.LOGGER.info("Removing Player from Room " + this.getCode());
        if (this.round != null) {
            RoundPlayer roundPlayer = this.round.getRoundPlayer(player.getUuid());
            if (roundPlayer != null) {
                this.round.removePlayer(roundPlayer, kicked ? Round.RemovePlayerReason.KICKED : Round.RemovePlayerReason.LEFT);
            }
        }
        this.leaderboard.removePlayer(player);
        this.players.remove(player);
        this.executeForPlayers(p -> p.removeRoomPlayer(player, kicked));

        if (this.players.size() == 0) {
            Main.getRoomManager().removeRoom(this);
            return;
        }
        if (player.getUuid().equals(this.owner)) {
            this.setOwner(this.players.get(0).getUuid());
        }
    }

    public void executeForPlayers(Player ignore, Consumer<Player> callback) {
        for (Player player : this.players) {
            if (player.equals(ignore)) continue;
            callback.accept(player);
        }
    }

    public void executeForPlayers(Consumer<Player> callback) {
        this.executeForPlayers(null, callback);
    }

    public enum State {
        LOBBY,
        INGAME
    }

    // This gets transferred over network
    @Getter
    @RequiredArgsConstructor
    public static class RoomData {
        private final String code;
        private final List<Player> players;
        private final UUID owner;
        private final Chat chat;
        private final Room.State state;
        private final Leaderboard leaderboard;
        private final RoundSettings roundSettings;
        private final CardDeck cardDeck;
        private final Round.RoundData round;

        public static RoomData fromRoom(Room room) {
            Round.RoundData roundData = null;
            if (room.getRound() != null) {
                roundData = Round.RoundData.fromRound(room.getRound());
            }
            return new RoomData(
                    room.getCode(),
                    room.getPlayers(),
                    room.getOwner(),
                    room.getChat(),
                    room.getState(),
                    room.getLeaderboard(),
                    room.getRoundSettings(),
                    room.getCardDeck(),
                    roundData
            );
        }
    }

}

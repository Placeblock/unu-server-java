package de.placeblock.unuserver.game;

import de.placeblock.unuserver.Main;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundSettings;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class Room {
    private final int code;
    private final List<Player> players = new ArrayList<>();
    private State state;
    private Leaderboard leaderboard;
    private RoundSettings roundSettings;
    private List<Card> cardStack;
    private Round round;

    public void setState(State state) {
        this.state = state;
        this.executeForPlayers(p -> p.setRoomState(this.state));
    }

    public void startRound() {
        this.round = new Round(this, this.roundSettings, this.players, this.cardStack);
        Round.RoundData roundData = Round.RoundData.fromRound(this.round);
        this.executeForPlayers(p -> p.setRoundData(roundData));
        this.setState(State.INGAME);
    }

    public void setCardStack(List<Card> cardStack) {
        this.cardStack = cardStack;
        this.executeForPlayers(p -> p.setCardStack(this.cardStack));
    }

    public void setRoundSettings(RoundSettings roundSettings) {
        this.roundSettings = roundSettings;
        this.executeForPlayers(p -> p.setRoundSettings(this.roundSettings));
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        player.setRoomData(RoomData.fromRoom(this));
        this.executeForPlayers(p -> p.sendPlayerData(player));
    }

    public void removePlayer(Player player, boolean kicked) {
        if (this.round != null) {
            this.round.removePlayer(player, kicked);
        }
        this.players.remove(player);
        this.executeForPlayers(p -> p.removeRoomPlayer(player, kicked));

        if (this.players.size() == 0) {
            Main.getRoomManager().removeRoom(this);
        }
    }

    public void executeForPlayers(Consumer<Player> callback) {
        for (Player player : this.players) {
            callback.accept(player);
        }
    }

    public enum State {
        LOBBY,
        INGAME,
        GAMERESULT
    }

    // This gets transferred over network
    @RequiredArgsConstructor
    public static class RoomData {
        private final List<Player> players;
        private final Room.State state;
        private final Round.RoundData round;

        public static RoomData fromRoom(Room room) {
            Round.RoundData roundData = null;
            if (room.getRound() != null) {
                roundData = Round.RoundData.fromRound(room.getRound());
            }
            return new RoomData(
                    room.getPlayers(),
                    room.getState(),
                    roundData
            );
        }
    }

}

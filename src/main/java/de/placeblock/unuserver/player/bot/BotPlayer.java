package de.placeblock.unuserver.player.bot;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.game.Leaderboard;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.game.round.RoundSettings;
import de.placeblock.unuserver.player.Inventory;
import de.placeblock.unuserver.player.Player;

import java.util.List;
import java.util.UUID;

public class BotPlayer extends Player {
    public BotPlayer(UUID uuid, String name) {
        super(uuid, name);
    }

    @Override
    public void setRoundSettings(RoundSettings roundSettings) {

    }

    @Override
    public void removeRoundPlayer(RoundPlayer player) {

    }

    @Override
    public void setPlayerCardAmount(RoundPlayer player, int amount) {

    }

    @Override
    public void setPlayedCard(Card card) {

    }

    @Override
    public void setInventory(Inventory inventory) {

    }

    @Override
    public void setDrawStack(List<Card> drawStack) {

    }

    @Override
    public void confirmDrawnCard(Card card, Round.DrawReason reason) {

    }

    @Override
    public void setCurrentPlayer(RoundPlayer roundPlayer) {

    }

    @Override
    public void showPlayerAcknowledgeLastCard(RoundPlayer roundPlayer) {

    }

    @Override
    public void sendPlayerData(Player player) {

    }

    @Override
    public void setJoinedRoom(Player player, Room.RoomData roomData) {

    }

    @Override
    public void setLeaderboard(Leaderboard leaderboard) {

    }

    @Override
    public void removeRoomPlayer(Player player) {

    }

    @Override
    public void setRoomState(Room.State state) {

    }
}

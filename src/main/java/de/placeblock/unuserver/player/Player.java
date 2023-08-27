package de.placeblock.unuserver.player;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.game.Leaderboard;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.game.round.RoundSettings;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
public abstract class Player {
    private final UUID uuid = UUID.randomUUID();
    private String name = "Noname";
    @Setter
    private Room room;

    public void setName(String name) {
        this.name = name;
        if (this.room != null) {
            this.room.executeForPlayers(p -> p.setPlayerName(this, name));
        }
    }

    public void remove() {
        if (this.room != null) {
            this.room.removePlayer(this, false);
        }
    }

    public abstract void setRoundSettings(RoundSettings roundSettings);
    public abstract void setCardStack(List<Card> cardStack);
    public abstract void removeRoundPlayer(RoundPlayer player, Round.RemovePlayerReason reason);
    public abstract void setPlayerCardAmount(RoundPlayer player, int amount);
    public abstract void setPlacedCard(Card card);
    public abstract void setInventory(Inventory inventory);
    public abstract void setDrawStack(int drawStack);
    public abstract void confirmDrawnCard(Card card, Round.DrawReason reason);
    public abstract void setCurrentPlayer(RoundPlayer roundPlayer);
    public abstract void showPlayerAcknowledgeLastCard(RoundPlayer roundPlayer);
    public abstract void sendPlayerData(Player player);
    public abstract void setLeaderboard(Leaderboard leaderboard);
    public abstract void removeRoomPlayer(Player player, boolean kicked);
    public abstract void setRoomState(Room.State state);
    public abstract void setRoomData(Room.RoomData roomData);
    public abstract void setCreatedRoomCode(int code);
    public abstract void setOwnPlayerData();
    public abstract void setPlayerName(Player player, String name);

}

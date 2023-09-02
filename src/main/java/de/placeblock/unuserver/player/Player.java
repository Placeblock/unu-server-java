package de.placeblock.unuserver.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.placeblock.unuserver.Main;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.CardDeck;
import de.placeblock.unuserver.communication.Message;
import de.placeblock.unuserver.communication.QuickReaction;
import de.placeblock.unuserver.game.Leaderboard;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.game.round.RoundSettings;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public abstract class Player {
    private final UUID uuid = UUID.randomUUID();
    private String name = "Noname";
    @Setter
    @JsonIgnore
    private Room room;

    public void setName(String name) {
        this.name = name;
        if (this.room != null) {
            this.room.executeForPlayers(p -> p.setPlayerName(this, name));
        } else {
            this.setPlayerName(this, name);
        }
    }

    public void remove() {
        Main.LOGGER.info("Removing WebSocketPlayer");
        if (this.room != null) {
            this.room.removePlayer(this, false);
            this.room = null;
        }
    }

    public abstract void setRoundSettings(RoundSettings roundSettings);
    public abstract void setCardDeck(CardDeck cardDeck);
    public abstract void removeRoundPlayer(RoundPlayer player, Round.RemovePlayerReason reason);
    public abstract void setPlayerCardAmount(RoundPlayer player, int amount);
    public abstract void setPlacedCard(Card<?> card);
    public abstract void setInventory(Inventory inventory);
    public abstract void setDrawStack(int drawStack);
    public abstract void addCard(Card<?> card, Round.AddCardReason reason);
    public abstract void setCurrentPlayer(RoundPlayer roundPlayer);
    public abstract void showPlayerAcknowledgeLastCard(RoundPlayer roundPlayer);
    public abstract void sendPlayerData(Player player);
    public abstract void updateLeaderboard(Leaderboard leaderboard);
    public abstract void removeRoomPlayer(Player player, boolean kicked);
    public abstract void setRoomState(Room.State state);
    public abstract void setRoomData(Room.RoomData roomData);
    public abstract void setOwnPlayerData();
    public abstract void setPlayerName(Player player, String name);
    public abstract void deleteMessage(UUID message);
    public abstract void sendMessage(Message message);
    public abstract void sendQuickReaction(Player player, QuickReaction quickReaction);
    public abstract void setOwner(UUID owner);
    public abstract void setRoundData(Round.RoundData roundData);
    public abstract void removeCard(UUID uuid);
    public abstract void setCardDeckPresets();
}

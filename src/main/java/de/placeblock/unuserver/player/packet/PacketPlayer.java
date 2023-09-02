package de.placeblock.unuserver.player.packet;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.CardDeck;
import de.placeblock.unuserver.communication.Message;
import de.placeblock.unuserver.communication.QuickReaction;
import de.placeblock.unuserver.game.Leaderboard;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.game.round.RoundSettings;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.packets.in.room.RoomRequiredPacket;
import de.placeblock.unuserver.packets.in.round.RoundRequiredPacket;
import de.placeblock.unuserver.packets.out.OutPacket;
import de.placeblock.unuserver.packets.out.communication.DeleteMessageOutPacket;
import de.placeblock.unuserver.packets.out.communication.MessageOutPacket;
import de.placeblock.unuserver.packets.out.communication.QuickReactionOutPacket;
import de.placeblock.unuserver.packets.out.player.OwnPlayerDataOutPacket;
import de.placeblock.unuserver.packets.out.player.PlayerDataOutPacket;
import de.placeblock.unuserver.packets.out.player.PlayerNameOutPacket;
import de.placeblock.unuserver.packets.out.room.*;
import de.placeblock.unuserver.packets.out.round.*;
import de.placeblock.unuserver.player.Inventory;
import de.placeblock.unuserver.player.Player;

import java.util.UUID;

public abstract class PacketPlayer extends Player {
    protected abstract void send(OutPacket packet);

    protected void onReceive(InPacket packet) {
        if (packet instanceof RoomRequiredPacket && this.getRoom() == null ||
            packet instanceof RoundRequiredPacket && this.getRoom().getRound() == null) return;
        packet.onReceive(this);
    }

    @Override
    public void setRoundSettings(RoundSettings roundSettings) {
        this.send(new RoundSettingsOutPacket(roundSettings));
    }

    @Override
    public void setCardDeck(CardDeck cardDeck) {
        this.send(new CardDeckOutPacket(cardDeck));
    }

    @Override
    public void removeRoundPlayer(RoundPlayer player, Round.RemovePlayerReason reason) {
        this.send(new PlayerLeftRoundOutPacket(player.getPlayer().getUuid(), reason));
    }

    @Override
    public void setPlayerCardAmount(RoundPlayer player, int amount) {
        this.send(new PlayerCardAmountOutPacket(player.getPlayer().getUuid(), amount));
    }

    @Override
    public void setCurrentCard(Card<?> card) {
        this.send(new CurrentCardOutPacket(card));
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.send(new InventoryOutPacket(inventory));
    }

    @Override
    public void setDrawStack(int drawStack) {
        this.send(new DrawStackOutPacket(drawStack));
    }

    @Override
    public void addCard(Card<?> card, Round.AddCardReason reason) {
        this.send(new AddCardOutPacket(card, reason));
    }

    @Override
    public void setCurrentPlayer(RoundPlayer roundPlayer) {
        this.send(new CurrentPlayerOutPacket(roundPlayer.getPlayer().getUuid()));
    }

    @Override
    public void showPlayerAcknowledgeLastCard(RoundPlayer roundPlayer) {
        this.send(new AcknowledgeLastCardOutPacket(roundPlayer.getPlayer().getUuid()));
    }

    @Override
    public void sendPlayerData(Player player) {
        this.send(new PlayerDataOutPacket(player));
    }

    @Override
    public void setOwnPlayerData() {
        this.send(new OwnPlayerDataOutPacket(this));
    }

    @Override
    public void setPlayerName(Player player, String name) {
        this.send(new PlayerNameOutPacket(player.getUuid(), name));
    }

    @Override
    public void deleteMessage(UUID message) {
        this.send(new DeleteMessageOutPacket(message));
    }

    @Override
    public void sendMessage(Message message) {
        this.send(new MessageOutPacket(message));
    }

    @Override
    public void sendQuickReaction(Player player, QuickReaction reaction) {
        this.send(new QuickReactionOutPacket(player.getUuid(), reaction));
    }

    @Override
    public void updateLeaderboard(Leaderboard leaderboard) {
        this.send(new LeaderboardOutPacket(leaderboard));
    }

    @Override
    public void removeRoomPlayer(Player player, boolean kicked) {
        this.send(new PlayerLeftRoomOutPacket(player.getUuid(), kicked));
    }

    @Override
    public void setRoomState(Room.State state) {
        this.send(new RoomStateOutPacket(state));
    }

    @Override
    public void setRoomData(Room.RoomData roomData) {
        this.send(new RoomDataOutPacket(roomData));
    }

    @Override
    public void setOwner(UUID owner) {
        this.send(new RoomOwnerOutPacket(owner));
    }

    @Override
    public void setRoundData(Round.RoundData roomData) {
        this.send(new RoundDataOutPacket(roomData));
    }

    @Override
    public void removeCard(UUID uuid) {
        this.send(new RemoveCardOutPacket(uuid));
    }

    @Override
    public void setCardDeckPresets() {
        this.send(new CardDeckPresetsOutPacket());
    }

    @Override
    public void selectColor() {
        this.send(new SelectColorOutPacket());
    }
}

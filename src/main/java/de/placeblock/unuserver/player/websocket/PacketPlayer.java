package de.placeblock.unuserver.player.websocket;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.game.Leaderboard;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.game.round.RoundSettings;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.packets.in.room.RoomRequiredPacket;
import de.placeblock.unuserver.packets.in.round.RoundRequiredPacket;
import de.placeblock.unuserver.packets.out.OutPacket;
import de.placeblock.unuserver.packets.out.player.OwnPlayerDataOutPacket;
import de.placeblock.unuserver.packets.out.player.PlayerDataOutPacket;
import de.placeblock.unuserver.packets.out.room.*;
import de.placeblock.unuserver.packets.out.round.*;
import de.placeblock.unuserver.player.Inventory;
import de.placeblock.unuserver.player.Player;

import java.util.List;
import java.util.UUID;

public abstract class PacketPlayer extends Player {
    public PacketPlayer(UUID uuid, String name) {
        super(uuid, name);
    }

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
    public void setCardStack(List<Card> cardStack) {

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
    public void setPlacedCard(Card card) {
        this.send(new PlayCardOutPacket(card));
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
    public void confirmDrawnCard(Card card, Round.DrawReason reason) {
        this.send(new DrawCardOutPacket(card, reason));
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
    public void sendOwnPlayerData(Player player) {
        this.send(new OwnPlayerDataOutPacket(player));
    }

    @Override
    public void setLeaderboard(Leaderboard leaderboard) {
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
    public void setRoundData(Round.RoundData roundData) {
        this.send(new RoundDataOutPacket(roundData));
    }

    @Override
    public void setCreatedRoomCode(int code) {
        this.send(new CreatedRoomOutPacket(code));
    }
}

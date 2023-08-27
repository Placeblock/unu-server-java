package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;

public class LeaveRoomInPacket extends InPacket implements RoomRequiredPacket {
    @Override
    public void onReceive(Player player) {
        player.getRoom().removePlayer(player, false);
    }
}

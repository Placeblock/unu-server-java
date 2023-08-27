package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.Main;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;

public class CreateRoomInPacket extends InPacket {
    @Override
    public void onReceive(Player player) {
        Room newRoom = Main.getRoomManager().createRoom();
        player.setCreatedRoomCode(newRoom.getCode());
    }
}

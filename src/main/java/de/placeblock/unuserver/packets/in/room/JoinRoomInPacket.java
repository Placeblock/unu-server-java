package de.placeblock.unuserver.packets.in.room;


import de.placeblock.unuserver.Main;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JoinRoomInPacket extends InPacket {
    private final int code;

    @Override
    public void onReceive(Player player) {
        Room room = Main.getRoomManager().getRoom(this.code);
        if (room == null) return;
        player.setRoom(room);
        room.addPlayer(player);
    }
}

package de.placeblock.unuserver.packets.out.room;

import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.packets.PlayerPacket;
import de.placeblock.unuserver.packets.out.OutPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JoinRoomOutPacket extends OutPacket implements PlayerPacket {
    private final Player player;
    private final Room.RoomData roomData;
}

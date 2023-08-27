package de.placeblock.unuserver.packets.out.room;

import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoomDataOutPacket extends OutPacket {
    private final Room.RoomData roomData;
}

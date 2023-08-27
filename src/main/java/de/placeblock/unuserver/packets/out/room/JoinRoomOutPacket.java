package de.placeblock.unuserver.packets.out.room;

import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.packets.PlayerPacket;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class JoinRoomOutPacket extends OutPacket implements PlayerPacket {
    private final UUID player;
    private final Room.RoomData roomData;
}

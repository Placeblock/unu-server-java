package de.placeblock.unuserver.packets.out.room;

import de.placeblock.unuserver.game.PublicRoomInfo;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoomVisibilityOutPacket extends OutPacket {
    private final PublicRoomInfo roomInfo;
    private final boolean publicRoom;
}

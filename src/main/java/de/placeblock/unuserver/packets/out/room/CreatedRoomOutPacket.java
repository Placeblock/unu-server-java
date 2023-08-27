package de.placeblock.unuserver.packets.out.room;

import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreatedRoomOutPacket extends OutPacket {
    private final int code;
}

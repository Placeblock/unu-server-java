package de.placeblock.unuserver.packets.out.room;

import de.placeblock.unuserver.game.PublicRoomInfo;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PublicRoomsOutPacket extends OutPacket {
    private final List<PublicRoomInfo> publicRoomInfos;
}

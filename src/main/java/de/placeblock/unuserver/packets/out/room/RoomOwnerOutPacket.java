package de.placeblock.unuserver.packets.out.room;

import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomOwnerOutPacket extends OutPacket {
    private UUID owner;
}

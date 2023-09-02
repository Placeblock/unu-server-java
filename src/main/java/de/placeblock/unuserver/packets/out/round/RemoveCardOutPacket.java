package de.placeblock.unuserver.packets.out.round;

import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class RemoveCardOutPacket extends OutPacket {

    private final UUID uuid;

}

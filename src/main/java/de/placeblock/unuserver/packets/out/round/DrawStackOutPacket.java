package de.placeblock.unuserver.packets.out.round;

import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DrawStackOutPacket extends OutPacket {
    private final int drawStack;
}

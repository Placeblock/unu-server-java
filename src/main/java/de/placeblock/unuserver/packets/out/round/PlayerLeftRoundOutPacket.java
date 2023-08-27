package de.placeblock.unuserver.packets.out.round;

import de.placeblock.unuserver.packets.PlayerPacket;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PlayerLeftRoundOutPacket extends OutPacket implements PlayerPacket {
    private final UUID player;
    private final boolean kicked;
}

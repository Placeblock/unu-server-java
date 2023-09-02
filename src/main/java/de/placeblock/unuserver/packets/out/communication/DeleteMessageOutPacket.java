package de.placeblock.unuserver.packets.out.communication;

import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DeleteMessageOutPacket extends OutPacket {
    private final UUID uuid;
}

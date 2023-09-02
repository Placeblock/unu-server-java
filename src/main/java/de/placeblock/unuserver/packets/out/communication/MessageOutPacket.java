package de.placeblock.unuserver.packets.out.communication;

import de.placeblock.unuserver.communication.Message;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageOutPacket extends OutPacket {
    private final Message message;
}

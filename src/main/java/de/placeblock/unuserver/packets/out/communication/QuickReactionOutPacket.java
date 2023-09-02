package de.placeblock.unuserver.packets.out.communication;

import de.placeblock.unuserver.communication.QuickReaction;
import de.placeblock.unuserver.packets.PlayerPacket;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
public class QuickReactionOutPacket extends OutPacket implements PlayerPacket {

    private final UUID player;
    private final QuickReaction quickReaction;

}

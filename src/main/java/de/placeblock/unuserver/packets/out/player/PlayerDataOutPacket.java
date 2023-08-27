package de.placeblock.unuserver.packets.out.player;

import de.placeblock.unuserver.packets.out.OutPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlayerDataOutPacket extends OutPacket {
    private final Player player;
}

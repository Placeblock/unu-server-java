package de.placeblock.unuserver.packets.out.round;

import de.placeblock.unuserver.packets.out.OutPacket;
import de.placeblock.unuserver.player.Inventory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InventoryOutPacket extends OutPacket {
    private final Inventory inventory;
}

package de.placeblock.unuserver.packets.out.room;

import de.placeblock.unuserver.cards.stackpresets.CardDeckPreset;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;

@Getter
public class CardDeckPresetsOutPacket extends OutPacket {

    private final CardDeckPreset[] presets = CardDeckPreset.values();

}

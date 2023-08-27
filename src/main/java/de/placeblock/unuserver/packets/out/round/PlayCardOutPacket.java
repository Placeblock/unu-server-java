package de.placeblock.unuserver.packets.out.round;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlayCardOutPacket extends OutPacket {
    private final Card card;
}

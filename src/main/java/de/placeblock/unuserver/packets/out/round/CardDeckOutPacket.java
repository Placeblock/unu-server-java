package de.placeblock.unuserver.packets.out.round;

import de.placeblock.unuserver.cards.CardDeck;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CardDeckOutPacket extends OutPacket {
    private final CardDeck cardDeck;
}

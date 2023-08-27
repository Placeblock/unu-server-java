package de.placeblock.unuserver.packets.out.round;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CardStackOutPacket extends OutPacket {
    private final List<Card> cardStack;
}

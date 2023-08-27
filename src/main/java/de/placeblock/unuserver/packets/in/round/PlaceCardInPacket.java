package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.packets.in.InPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class PlaceCardInPacket extends InPacket implements RoundRequiredPacket {
    private final Card card;
}

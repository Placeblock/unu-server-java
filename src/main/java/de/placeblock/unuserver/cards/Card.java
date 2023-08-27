package de.placeblock.unuserver.cards;

import de.placeblock.unuserver.game.round.Round;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class Card implements Serializable {

    private final UUID uuid;

    public abstract boolean isValidNextCard(Card card);

    public abstract void place(Round round);

}

package de.placeblock.unuserver.cards.impl;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.Colored;
import de.placeblock.unuserver.game.round.Round;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SuspendCard extends Card implements Colored {
    private final Color color;

    public SuspendCard(UUID uuid, Color color) {
        super(uuid);
        this.color = color;
    }

    @Override
    public boolean isValidNextCard(Card card) {
        return false;
    }

    @Override
    public void place(Round round) {

    }
}

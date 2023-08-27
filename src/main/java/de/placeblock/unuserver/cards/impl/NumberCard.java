package de.placeblock.unuserver.cards.impl;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.Colored;
import de.placeblock.unuserver.cards.Numbered;
import de.placeblock.unuserver.game.round.Round;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NumberCard extends Card implements Colored, Numbered {
    protected final int number;
    protected final Color color;

    public NumberCard(UUID uuid, int number, Color color) {
        super(uuid);
        this.number = number;
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

package de.placeblock.unuserver.cards.impl;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.ForceColorCard;
import de.placeblock.unuserver.game.round.Round;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WishCard extends Card implements ForceColorCard {
    private final Color forceColor;

    public WishCard(UUID uuid, Color forceColor) {
        super(uuid);
        this.forceColor = forceColor;
    }

    @Override
    public boolean isValidNextCard(Card card) {
        return false;
    }

    @Override
    public void place(Round round) {

    }
}

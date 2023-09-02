package de.placeblock.unuserver.cards.stackpresets;

import de.placeblock.unuserver.cards.CardDeck;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.impl.*;

public class OnlyNumberCardDeck extends CardDeck {

    public OnlyNumberCardDeck() {
        for (Color color : Color.values()) {
            CardGroup colorGroup = new CardGroup();
            for (int i = 0; i < 10; i++) {
                colorGroup.addCard(new NumberCard(i, color));
            }
            colorGroup.setAmount(4);
            this.getGroups().add(colorGroup);
        }
    }

}

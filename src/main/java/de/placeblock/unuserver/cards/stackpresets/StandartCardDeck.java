package de.placeblock.unuserver.cards.stackpresets;

import de.placeblock.unuserver.cards.CardDeck;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.impl.*;

public class StandartCardDeck extends CardDeck {

    public StandartCardDeck() {
        for (Color color : Color.values()) {
            CardGroup colorGroup = new CardGroup();
            for (int i = 0; i < 10; i++) {
                colorGroup.addCard(new NumberCard(i, color));
            }
            colorGroup.addCard(new SuspendCard(color));
            colorGroup.addCard(new InvertDirectionCard(color));
            colorGroup.addCard(new Draw2Card(color));
            colorGroup.setAmount(2);
            this.getGroups().add(colorGroup);
        }
        CardGroup specialGroup = new CardGroup();
        specialGroup.addCard(new Draw4Card());
        specialGroup.addCard(new WishCard());
        specialGroup.setAmount(4);
        this.getGroups().add(specialGroup);
    }

}

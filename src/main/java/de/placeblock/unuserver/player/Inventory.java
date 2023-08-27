package de.placeblock.unuserver.player;

import de.placeblock.unuserver.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final List<Card> cards = new ArrayList<>();

    public int size() {
        return this.cards.size();
    }

}

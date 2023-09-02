package de.placeblock.unuserver.cards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDeck {
    private List<CardGroup> groups = new ArrayList<>();

    public List<Card<?>> flatten() {
        List<Card<?>> cards = new ArrayList<>();
        for (CardGroup group : this.groups) {
            for (int i = 0; i < group.getAmount(); i++) {
                for (Card<?> card : group.getCards()) {
                    cards.add(card.copy());
                }
            }
        }
        return cards;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardGroup {
        private List<Card<?>> cards = new ArrayList<>();
        private int amount = 1;

        public void addCard(Card<?> card) {
            this.cards.add(card);
        }
    }

}

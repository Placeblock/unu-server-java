package de.placeblock.unuserver.cards.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.placeblock.unuserver.cards.*;
import de.placeblock.unuserver.game.round.Round;
import lombok.Getter;

@Getter
@JsonTypeName("number")
public class NumberCard extends Card<NumberCard> implements Colored, Numbered, DrawStackApplier {
    protected final int number;
    protected final Color color;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NumberCard(@JsonProperty("number") int number, @JsonProperty("color") Color color) {
        this.number = number;
        this.color = color;
    }

    @Override
    public boolean isValidNextCard(Round round, Card<?> card) {
        if (card instanceof Colored || card instanceof Numbered) {
            if (card instanceof Numbered numbered) {
                System.out.println("---------");
                System.out.println(this.number);
                System.out.println(numbered.getNumber());
            }
            if (card instanceof Colored colored && this.color == colored.getColor()) {
                return true;
            } else return card instanceof Numbered numbered && this.number == numbered.getNumber();
        }
        return true;
    }

    @Override
    public void place(Round round) {

    }

    @Override
    public boolean canBeginWith() {
        return true;
    }

    @Override
    public NumberCard copy() {
        return new NumberCard(this.number, this.color);
    }
}

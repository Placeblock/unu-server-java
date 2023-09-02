package de.placeblock.unuserver.cards.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.Colored;
import de.placeblock.unuserver.cards.DrawStackApplier;
import de.placeblock.unuserver.game.round.Round;
import lombok.Getter;

@Getter
@JsonTypeName("suspend")
public class SuspendCard extends Card<SuspendCard> implements Colored, DrawStackApplier {
    private final Color color;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SuspendCard(@JsonProperty("color") Color color) {
        this.color = color;
    }

    @Override
    public boolean isValidNextCard(Round round, Card<?> card) {
        return !(card instanceof Colored colored) || this.color == colored.getColor();
    }

    @Override
    public void place(Round round) {

    }

    @Override
    public boolean canBeginWith() {
        return false;
    }

    @Override
    public SuspendCard copy() {
        return new SuspendCard(this.color);
    }
}

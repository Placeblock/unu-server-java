package de.placeblock.unuserver.cards.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.Colored;
import de.placeblock.unuserver.game.round.Round;
import lombok.Getter;

@Getter
@JsonTypeName("draw_2")
public class Draw2Card extends Card<Draw2Card> implements Colored {
    private final Color color;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Draw2Card(@JsonProperty("color") Color color) {
        this.color = color;
    }

    @Override
    public boolean isValidNextCard(Round round, Card<?> card) {
        if ((card instanceof Colored colored && this.color == colored.getColor()) ||
            card instanceof Draw2Card) return true;
        return card instanceof Draw4Card && round.getRoundSettings().isPlus4OnPlus2();
    }

    @Override
    public void place(Round round) {
        round.setDrawStack(round.getDrawStack()+2);
        round.setNextPlayer(round.calculateNextPlayer());
    }

    @Override
    public boolean canBeginWith() {
        return false;
    }

    @Override
    public Draw2Card copy() {
        return new Draw2Card(this.color);
    }
}

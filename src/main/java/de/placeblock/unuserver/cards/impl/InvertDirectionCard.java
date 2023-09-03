package de.placeblock.unuserver.cards.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.Colored;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.move.Move;
import lombok.Getter;

@Getter
@JsonTypeName("invert_direction")
public class InvertDirectionCard extends Card<InvertDirectionCard> implements Colored {
    private final Color color;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public InvertDirectionCard(@JsonProperty("color") Color color) {
        this.color = color;
    }

    @Override
    public boolean isValidNextCard(Round round, Card<?> card) {
        return (!(card instanceof Colored colored) || this.color == colored.getColor()) ||
                    card instanceof InvertDirectionCard;
    }

    @Override
    public void place(Round round) {
        round.applyDrawStack();
        round.setDirection(round.getDirection()*-1);
        Move currentMove = round.getCurrentMove();
        boolean skipOn2Players = round.getRoundSettings().isSkipOnInverse2Players();
        int delta = (round.getPlayers().size()==2&&skipOn2Players)?2:1;
        currentMove.setNextPlayerDelta(delta);
        round.setNextPlayer(round.calculateNextPlayer());
    }

    @Override
    public boolean canBeginWith() {
        return false;
    }

    @Override
    public InvertDirectionCard copy() {
        return new InvertDirectionCard(this.color);
    }
}

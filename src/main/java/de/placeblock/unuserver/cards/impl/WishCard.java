package de.placeblock.unuserver.cards.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import de.placeblock.unuserver.cards.*;
import de.placeblock.unuserver.game.round.Round;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("wish")
public class WishCard extends Card<WishCard> implements ForceColorCard, DrawStackApplier {
    private Color forceColor;

    @Override
    public boolean isValidNextCard(Round round, Card<?> card) {
        return (card instanceof Colored colored && this.forceColor == colored.getColor()) ||
                (card instanceof WishCard && round.getRoundSettings().isWishOnWish()) ||
                (card instanceof Draw4Card && round.getRoundSettings().isPlus4OnWish());
    }

    @Override
    public void place(Round round) {

    }

    @Override
    public boolean canBeginWith() {
        return false;
    }

    @Override
    public WishCard copy() {
        return new WishCard(this.forceColor);
    }
}

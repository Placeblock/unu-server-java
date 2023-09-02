package de.placeblock.unuserver.cards;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.placeblock.unuserver.cards.impl.*;
import de.placeblock.unuserver.game.round.Round;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(Draw2Card.class),
        @JsonSubTypes.Type(Draw4Card.class),
        @JsonSubTypes.Type(InvertDirectionCard.class),
        @JsonSubTypes.Type(NumberCard.class),
        @JsonSubTypes.Type(SuspendCard.class),
        @JsonSubTypes.Type(WishCard.class),
})
@Getter
public abstract class Card<C extends Card<C>> implements Serializable {

    private final UUID uuid = UUID.randomUUID();

    public abstract boolean isValidNextCard(Round round, Card<?> card);

    public abstract void place(Round round);

    public abstract boolean canBeginWith();

    public abstract C copy();
}

package de.placeblock.unuserver.cards.stackpresets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.placeblock.unuserver.cards.CardDeck;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CardDeckPreset {

    STANDARD("Standard", "The default deck", StandartCardDeck::new),
    ONLY_NUMBER("Only numbers", "Deck with just number-cards", OnlyNumberCardDeck::new);

    @JsonProperty
    private final String name;
    @JsonProperty
    private final String description;
    @JsonIgnore
    private final Supplier<CardDeck> cardDeck;

}

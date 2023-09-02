package de.placeblock.unuserver.game.round;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoundSettings {
    private int startCardAmount;
    private boolean plus4OnPlus4;
    private boolean plus2OnPlus4;
    private boolean plus4OnPlus2;
    private boolean wishOnPlus4;
    private boolean plus4OnWish;
    private boolean wishOnWish;
}

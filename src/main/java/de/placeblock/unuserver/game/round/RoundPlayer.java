package de.placeblock.unuserver.game.round;

import de.placeblock.unuserver.player.Inventory;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class RoundPlayer {

    private final Player player;
    @Setter
    private Inventory inventory;
    private int placedCards = 0;

    public void incPlacedCards() {
        this.placedCards++;
    }

}

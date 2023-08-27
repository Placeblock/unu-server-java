package de.placeblock.unuserver.game.round;

import de.placeblock.unuserver.player.Inventory;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoundPlayer {

    private final Player player;
    private final Inventory inventory;
    private int placedCards = 0;

    public void incPlacedCards() {
        this.placedCards++;
    }

}

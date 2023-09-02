package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectColorInPacket extends InPacket implements RoundRequiredPacket {

    private Color color;

    @Override
    public void onReceive(Player player) {
        Round round = player.getRoom().getRound();
        round.getCurrentMove().callPacketHandler(this, player);
    }
}

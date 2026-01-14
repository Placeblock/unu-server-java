package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;

public class SkipMoveInPacket extends InPacket implements RoundRequiredPacket {
    @Override
    public void onReceive(Player player) {
        Round round = player.getRoom().getRound();
        round.getCurrentMove().callPacketHandler(this, player);
    }
}

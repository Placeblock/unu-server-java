package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;

public class LeaveRoundInPacket extends InPacket implements RoundRequiredPacket {
    @Override
    public void onReceive(Player player) {
        player.getRoom().getRound().removePlayer(player, false);
    }
}

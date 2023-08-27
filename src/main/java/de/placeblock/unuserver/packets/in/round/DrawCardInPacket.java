package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;

public class DrawCardInPacket extends InPacket implements RoundRequiredPacket {
    @Override
    public void onReceive(Player player) {
        player.getRoom().getRound().drawCard()
    }
}

package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;

public class StartRoundInPacket extends InPacket implements RoomRequiredPacket {
    @Override
    public void onReceive(Player player) {
        player.getRoom().startRound();
    }
}

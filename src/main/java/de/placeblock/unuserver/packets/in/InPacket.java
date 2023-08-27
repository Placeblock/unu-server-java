package de.placeblock.unuserver.packets.in;

import de.placeblock.unuserver.packets.Packet;
import de.placeblock.unuserver.player.Player;

public abstract class InPacket implements Packet {

    public abstract void onReceive(Player player);

}

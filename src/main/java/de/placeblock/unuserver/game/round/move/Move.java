package de.placeblock.unuserver.game.round.move;

import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.packets.in.InPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Move {

    private final Round round;
    private final RoundPlayer player;
    private final Map<Class<? extends InPacket>, Consumer<InPacket>> packetHandlers = new HashMap<>();

    public Move(Round round, RoundPlayer player) {
        this.round = round;
        this.player = player;
    }

    public <P extends InPacket> void registerPacketHandler(Class<P> packetClass, Consumer<P> consumer) {
        this.packetHandlers.put(packetClass, packet -> consumer.accept(packetClass.cast(consumer)));
    }

    public void callPacketHandler(InPacket packet) {
        this.packetHandlers.get(packet.getClass()).accept(packet);
    }

}

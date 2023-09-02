package de.placeblock.unuserver.game.round.move;

import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.packets.in.round.DrawCardInPacket;
import de.placeblock.unuserver.packets.in.round.PlaceCardInPacket;
import de.placeblock.unuserver.packets.in.round.SkipMoveInPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class Move {

    private final Round round;
    private final RoundPlayer roundPlayer;
    private final Map<Class<? extends InPacket>, Consumer<InPacket>> packetHandlers = new HashMap<>();
    private boolean hasDrawnCard = false;
    @Setter
    private int nextPlayerDelta;

    public Move(Round round, RoundPlayer roundPlayer) {
        this.round = round;
        this.roundPlayer = roundPlayer;
        this.nextPlayerDelta = round.getDirection();
        this.registerPacketHandler(DrawCardInPacket.class, packet -> {
            if (this.hasDrawnCard) return;
            this.round.drawCard(roundPlayer);
            this.hasDrawnCard = true;
        });
        this.registerPacketHandler(PlaceCardInPacket.class, packet ->
            this.round.placeCard(this.roundPlayer, packet.getUuid()));
        this.registerPacketHandler(SkipMoveInPacket.class, packet -> {
            if (!this.hasDrawnCard) return;
            this.round.setNextPlayer(this.round.calculateNextPlayer());
        });
    }

    public <P extends InPacket> void registerPacketHandler(Class<P> packetClass, Consumer<P> consumer) {
        this.packetHandlers.put(packetClass, (packet) -> consumer.accept(packetClass.cast(packet)));
    }

    public void callPacketHandler(InPacket packet, Player player) {
        if (!player.equals(this.roundPlayer.getPlayer())) return;
        Consumer<InPacket> handler = this.packetHandlers.get(packet.getClass());
        if (handler == null) return;
        handler.accept(packet);
    }

}

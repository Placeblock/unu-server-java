package de.placeblock.unuserver.player.packet.websocket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.placeblock.unuserver.Main;
import de.placeblock.unuserver.packets.out.OutPacket;
import de.placeblock.unuserver.packets.out.player.OwnPlayerDataOutPacket;
import de.placeblock.unuserver.player.packet.PacketPlayer;
import lombok.Getter;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

@Getter
public class WebSocketPlayer extends PacketPlayer {
    @JsonIgnore
    private final Session session;

    public WebSocketPlayer(Session session) {
        Main.LOGGER.info("New WebSocketPlayer");
        this.session = session;
        this.setOwnPlayerData();
        this.send(new OwnPlayerDataOutPacket(this));
    }

    @Override
    protected void send(OutPacket packet) {
        try {
            String serialized = WebSocketEndpoint.objectMapper.writeValueAsString(packet);
            RemoteEndpoint basicRemote = this.session.getRemote();
            basicRemote.sendString(serialized);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package de.placeblock.unuserver.player.packet.websocket;

import com.google.gson.Gson;
import de.placeblock.unuserver.packets.out.OutPacket;
import de.placeblock.unuserver.packets.out.player.OwnPlayerDataOutPacket;
import de.placeblock.unuserver.player.packet.PacketPlayer;
import lombok.Getter;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;

@Getter
public class WebSocketPlayer extends PacketPlayer {
    private final Session session;
    private static final Gson gson = new Gson();

    public WebSocketPlayer(Session session) {
        this.session = session;
        this.send(new OwnPlayerDataOutPacket(this));
    }

    @Override
    protected void send(OutPacket packet) {
        String serialized = gson.toJson(packet);
        RemoteEndpoint.Basic basicRemote = this.session.getBasicRemote();
        try {
            basicRemote.sendText(serialized);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

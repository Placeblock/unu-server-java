package de.placeblock.unuserver.player.packet.websocket;


import de.placeblock.unuserver.player.Player;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/")
public class WebSocketEndpoint {
    private final Map<Session, WebSocketPlayer> players = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        WebSocketPlayer webSocketPlayer = new WebSocketPlayer(session);
        this.players.put(session, webSocketPlayer);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        Player player = this.players.get(session);
        if (player == null) return;

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws IOException {
        Player player = this.players.remove(session);
        if (player == null) return;
        player.remove();
    }

    @OnError
    public void onError(Session session, Throwable throwable) throws Throwable {
        Player player = this.players.remove(session);
        if (player != null) {
            player.remove();
        }
        throw throwable;
    }
}

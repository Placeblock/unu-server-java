package de.placeblock.unuserver.player.packet.websocket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.packets.in.InPacketRegistry;
import de.placeblock.unuserver.player.Player;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebSocket
@SuppressWarnings({"unused", "RedundantThrows"})
public class WebSocketEndpoint {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
    private final Map<Session, WebSocketPlayer> players = new HashMap<>();

    @OnWebSocketConnect
    public void onOpen(Session session) throws IOException {
        WebSocketPlayer webSocketPlayer = new WebSocketPlayer(session);
        this.players.put(session, webSocketPlayer);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        if (message.equals("ping")) {
            session.getRemote().sendString("pong");
            return;
        }
        Player player = this.players.get(session);
        if (player == null) return;
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            JsonNode actionNode = jsonNode.get("action");
            if (actionNode.isNull()) return;
            String action = actionNode.textValue();
            Class<? extends InPacket> packetClass = InPacketRegistry.getPacketClass(action);
            if (packetClass == null) return;
            JsonNode dataNode = jsonNode.get("data");
            if (dataNode.isNull()) return;
            InPacket inPacket = objectMapper.treeToValue(dataNode, packetClass);
            if (inPacket == null) return;
            inPacket.onReceive(player);
        } catch (JsonProcessingException e) {
            for (StackTraceElement element : e.getStackTrace()) {
                System.out.println(element);
            }
            System.out.println("THERE WAS AN JSON EXCEPTION");
        } catch (RuntimeException e) {
            for (StackTraceElement element : e.getStackTrace()) {
                System.out.println(element);
            }
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) throws IOException {
        Player player = this.players.remove(session);
        if (player == null) return;
        player.remove();
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) throws Throwable {
        Player player = this.players.remove(session);
        if (player != null) {
            player.remove();
        }
        throw throwable;
    }
}

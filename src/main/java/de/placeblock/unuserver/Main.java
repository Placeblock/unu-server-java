package de.placeblock.unuserver;

import de.placeblock.unuserver.player.packet.websocket.WebSocketEndpoint;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.util.logging.Logger;

public class Main {
    private static RoomManager roomManager;
    public static final Logger LOGGER = Logger.getLogger("unu");

    public static void main(String[] args) throws Exception {
        roomManager = new RoomManager();
        Server server = new Server(8050);

        server.setHandler(new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(WebSocketEndpoint.class);
            }
        });

        server.start();
        server.join();
    }

    public static RoomManager getRoomManager() {
        return roomManager;
    }

}

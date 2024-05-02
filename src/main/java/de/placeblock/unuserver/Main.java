package de.placeblock.unuserver;

import de.placeblock.unuserver.player.packet.websocket.WebSocketEndpoint;
import lombok.Getter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.util.logging.Logger;

public class Main {
    @Getter
    private static RoomManager roomManager;
    @Getter
    private static PlayerManager playerManager;

    public static final Logger LOGGER = Logger.getLogger("unu");

    public static void main(String[] args) throws Exception {
        roomManager = new RoomManager();
        playerManager = new PlayerManager();

        new Thread(() -> {

        }).start();

        Server server = new Server(9101);

        server.setHandler(new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(WebSocketEndpoint.class);
            }
        });

        server.start();
        server.join();
    }

}

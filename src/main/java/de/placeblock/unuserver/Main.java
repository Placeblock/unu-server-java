package de.placeblock.unuserver;

import de.placeblock.unuserver.player.packet.websocket.WebSocketEndpoint;
import lombok.Getter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.net.InetSocketAddress;
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

        InetSocketAddress address = new InetSocketAddress("localhost", 9101);
        Server server = new Server(address);

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

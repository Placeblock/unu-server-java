package de.placeblock.unuserver;

import de.placeblock.unuserver.game.PublicRoomInfo;
import de.placeblock.unuserver.game.Room;

import java.util.*;

public class RoomManager {
    private final Random random = new Random();
    private final Map<String, Room> rooms = new HashMap<>();

    public void removeRoom(Room room) {
        Main.LOGGER.info("Removing Room " + room.getCode());
        if (room.isPublicRoom()) {
            Main.getPlayerManager().forEach(p -> p.updateRoomVisibility(room.getPublicRoomInfo(), false));
        }
        this.rooms.remove(room.getCode());
    }

    public Room getRoom(String code) {
        return this.rooms.get(code);
    }

    public List<PublicRoomInfo> getPublicRoomInfos() {
        List<PublicRoomInfo> roomInfos = new ArrayList<>();
        for (Room room : this.rooms.values()) {
            if (room.isPublicRoom()) {
                roomInfos.add(room.getPublicRoomInfo());
            }
        }
        return roomInfos;
    }

    public Room createRoom() {
        String code = this.generateRoomCode();
        Room room = new Room(code);
        Main.LOGGER.info("Creating Room " + room.getCode());
        this.rooms.put(code, room);
        return room;
    }

    private String generateRoomCode() {
        String code = String.format("%04d", random.nextInt(10000));
        if (!this.rooms.containsKey(code)) {
            return code;
        } else {
            return generateRoomCode();
        }
    }

}

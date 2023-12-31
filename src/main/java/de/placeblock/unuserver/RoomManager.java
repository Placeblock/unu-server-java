package de.placeblock.unuserver;

import de.placeblock.unuserver.game.Room;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    private final Map<Integer, Room> rooms = new HashMap<>();

    public void removeRoom(Room room) {
        Main.LOGGER.info("Removing Room " + room.getCode());
        this.rooms.remove(room.getCode());
    }

    public Room getRoom(int code) {
        return this.rooms.get(code);
    }

    public Room createRoom() {
        int code = this.generateRoomCode();
        Room room = new Room(code);
        Main.LOGGER.info("Creating Room " + room.getCode());
        this.rooms.put(code, room);
        return room;
    }

    private int generateRoomCode() {
        //TODO: IMPLEMENT
        return 0;
    }

}

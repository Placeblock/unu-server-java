package de.placeblock.unuserver;

public class Main {
    private static RoomManager roomManager;

    public static void main(String[] args) {
        roomManager = new RoomManager();
    }

    public static RoomManager getRoomManager() {
        return roomManager;
    }

}

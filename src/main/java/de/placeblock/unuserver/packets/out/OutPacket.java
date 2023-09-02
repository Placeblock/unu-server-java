package de.placeblock.unuserver.packets.out;

import de.placeblock.unuserver.packets.Packet;

public abstract class OutPacket implements Packet {

    public String getAction() {
        String className = this.getClass().getSimpleName();
        String packetName = className.substring(0, className.length()-9);
        String[] words = packetName.split("(?=\\p{Upper})");
        return String.join("_", words).toLowerCase();
    }

}

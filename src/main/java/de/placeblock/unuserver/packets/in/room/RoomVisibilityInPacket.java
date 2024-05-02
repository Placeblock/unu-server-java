package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomVisibilityInPacket extends InPacket implements RoomRequiredPacket {
    private boolean publicRoom;

    @Override
    public void onReceive(Player player) {
        Room room = player.getRoom();
        if (room.getOwner() != player.getUuid()) return;
        room.setPublic(this.publicRoom);
    }
}

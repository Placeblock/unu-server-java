package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KickRoomPlayerInPacket extends InPacket implements RoomRequiredPacket {
    private UUID player;

    @Override
    public void onReceive(Player player) {
        player.getRoom().removePlayer(player, true);
    }
}

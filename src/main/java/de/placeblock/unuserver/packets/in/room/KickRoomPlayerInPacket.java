package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class KickRoomPlayerInPacket extends InPacket implements RoomRequiredPacket {
    private final UUID player;

    @Override
    public void onReceive(Player player) {
        player.getRoom().removePlayer(player, true);
    }
}

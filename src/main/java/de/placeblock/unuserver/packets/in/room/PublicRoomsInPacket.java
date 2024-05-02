package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.Main;
import de.placeblock.unuserver.game.PublicRoomInfo;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PublicRoomsInPacket extends InPacket {
    @Override
    public void onReceive(Player player) {
        List<PublicRoomInfo> publicRoomInfos = Main.getRoomManager().getPublicRoomInfos();
        player.sendPublicRooms(publicRoomInfos);
    }
}

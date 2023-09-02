package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StartRoundInPacket extends InPacket implements RoomRequiredPacket {
    @Override
    public void onReceive(Player player) {
        player.getRoom().startRound();
    }
}

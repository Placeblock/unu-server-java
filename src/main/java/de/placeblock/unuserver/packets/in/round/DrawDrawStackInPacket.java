package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DrawDrawStackInPacket extends InPacket implements RoundRequiredPacket {
    @Override
    public void onReceive(Player player) {
        Room room = player.getRoom();
        Round round = room.getRound();
        if (round.getCurrentPlayer().getPlayer().equals(player)) {
            round.applyDrawStack();
        }
    }
}

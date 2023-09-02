package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LeaveRoundInPacket extends InPacket implements RoundRequiredPacket {
    @Override
    public void onReceive(Player player) {
        Round round = player.getRoom().getRound();
        RoundPlayer roundPlayer = round.getRoundPlayer(player.getUuid());
        if (roundPlayer == null) return;
        round.removePlayer(roundPlayer, Round.RemovePlayerReason.LEFT);
    }
}

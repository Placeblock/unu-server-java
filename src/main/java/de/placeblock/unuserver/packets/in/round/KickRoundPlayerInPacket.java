package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.packets.PlayerPacket;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KickRoundPlayerInPacket extends InPacket implements PlayerPacket, RoundRequiredPacket {
    private UUID player;

    @Override
    public void onReceive(Player player) {
        Round round = player.getRoom().getRound();
        RoundPlayer roundPlayer = round.getRoundPlayer(player.getUuid());
        if (roundPlayer == null) return;
        round.removePlayer(roundPlayer, Round.RemovePlayerReason.KICKED);
    }
}

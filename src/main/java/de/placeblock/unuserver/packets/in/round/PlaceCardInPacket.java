package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceCardInPacket extends InPacket implements RoundRequiredPacket {
    private UUID uuid;

    @Override
    public void onReceive(Player player) {
        Round round = player.getRoom().getRound();
        round.getCurrentMove().callPacketHandler(this, player);
    }
}

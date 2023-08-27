package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.packets.PlayerPacket;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class KickRoundPlayerInPacket extends InPacket implements PlayerPacket, RoundRequiredPacket {
    private final UUID player;

    @Override
    public void onReceive(Player player) {
        player.getRoom().getRound().removePlayer(player, true);
    }
}

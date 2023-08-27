package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.game.round.RoundSettings;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoundSettingsInPacket extends InPacket implements RoomRequiredPacket {
    private final RoundSettings roundSettings;

    @Override
    public void onReceive(Player player) {
        player.getRoom().setRoundSettings(this.roundSettings);
    }
}

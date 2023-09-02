package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.game.round.RoundSettings;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoundSettingsInPacket extends InPacket implements RoomRequiredPacket {
    private RoundSettings roundSettings;

    @Override
    public void onReceive(Player player) {
        player.getRoom().setRoundSettings(this.roundSettings);
    }
}

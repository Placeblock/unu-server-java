package de.placeblock.unuserver.packets.out.round;

import de.placeblock.unuserver.game.round.RoundSettings;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoundSettingsOutPacket extends OutPacket {
    private final RoundSettings roundSettings;
}

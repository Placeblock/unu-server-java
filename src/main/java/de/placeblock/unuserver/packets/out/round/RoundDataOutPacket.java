package de.placeblock.unuserver.packets.out.round;

import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoundDataOutPacket extends OutPacket {
    private final Round.RoundData roundData;
}

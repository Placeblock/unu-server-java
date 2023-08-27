package de.placeblock.unuserver.packets.out.room;

import de.placeblock.unuserver.game.Leaderboard;
import de.placeblock.unuserver.packets.out.OutPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LeaderboardOutPacket extends OutPacket {
    private final Leaderboard leaderboard;
}

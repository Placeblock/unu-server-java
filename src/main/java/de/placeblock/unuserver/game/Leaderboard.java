package de.placeblock.unuserver.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
public class Leaderboard {
    @JsonIgnore
    private final Room room;
    private final Map<UUID, Integer> wins = new HashMap<>();

    public void removePlayer(Player player) {
        this.wins.remove(player.getUuid());
        this.update();
    }

    public void addWin(Player player) {
        UUID playerUuid = player.getUuid();
        if (this.wins.containsKey(playerUuid)) {
            this.wins.put(playerUuid, this.wins.get(playerUuid) + 1);
        } else {
            this.wins.put(playerUuid, 1);
        }
        this.update();
    }

    private void update() {
        this.room.executeForPlayers(p -> p.updateLeaderboard(this));
    }
}

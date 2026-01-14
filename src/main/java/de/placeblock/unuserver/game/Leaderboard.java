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
    private final Map<UUID, Integer> points = new HashMap<>();

    public void removePlayer(Player player) {
        this.points.remove(player.getUuid());
        this.update();
    }

    public void addPoints(Player player, int points) {
        UUID playerUuid = player.getUuid();
        if (this.points.containsKey(playerUuid)) {
            this.points.put(playerUuid, this.points.get(playerUuid) + points);
        } else {
            this.points.put(playerUuid, points);
        }
        this.update();
    }

    private void update() {
        this.room.executeForPlayers(p -> p.updateLeaderboard(this));
    }
}

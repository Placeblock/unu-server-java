package de.placeblock.unuserver;

import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;


@Getter
public class PlayerManager implements Iterable<Player> {

    private final Map<UUID, Player> players = new HashMap<>();

    public void addPlayer(Player player) {
        this.players.put(player.getUuid(), player);
    }

    public void removePlayer(UUID uuid) {
        this.players.remove(uuid);
    }

    @Override @NonNull
    public Iterator<Player> iterator() {
        return this.players.values().iterator();
    }

}

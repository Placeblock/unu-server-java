package de.placeblock.unuserver.packets.in.player;

import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlayerNameInPacket extends InPacket {
    private final String name;

    @Override
    public void onReceive(Player player) {
        player.setName(this.name);
    }
}

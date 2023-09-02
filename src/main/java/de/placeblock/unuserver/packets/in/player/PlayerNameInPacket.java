package de.placeblock.unuserver.packets.in.player;

import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerNameInPacket extends InPacket {
    private String name;

    @Override
    public void onReceive(Player player) {
        player.setName(this.name);
    }
}

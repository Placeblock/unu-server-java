package de.placeblock.unuserver.packets.in.chat;

import de.placeblock.unuserver.communication.QuickReaction;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.packets.in.room.RoomRequiredPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuickReactionInPacket extends InPacket implements RoomRequiredPacket {
    private QuickReaction quickReaction;

    @Override
    public void onReceive(Player player) {
        player.getRoom().executeForPlayers(p -> p.sendQuickReaction(player, this.quickReaction));
    }
}

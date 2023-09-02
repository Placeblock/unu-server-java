package de.placeblock.unuserver.packets.in.chat;

import de.placeblock.unuserver.communication.Chat;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.packets.in.room.RoomRequiredPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageInPacket extends InPacket implements RoomRequiredPacket {
    private String message;

    @Override
    public void onReceive(Player player) {
        Chat chat = player.getRoom().getChat();
        chat.sendMessage(player, this.message);
    }
}

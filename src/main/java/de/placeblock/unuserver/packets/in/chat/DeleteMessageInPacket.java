package de.placeblock.unuserver.packets.in.chat;

import de.placeblock.unuserver.communication.Chat;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.packets.in.room.RoomRequiredPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMessageInPacket extends InPacket implements RoomRequiredPacket {
    private UUID uuid;
    @Override
    public void onReceive(Player player) {
        Chat chat = player.getRoom().getChat();
        chat.deleteMessage(this.uuid);
    }
}

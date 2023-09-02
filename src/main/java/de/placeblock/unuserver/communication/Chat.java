package de.placeblock.unuserver.communication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class Chat {
    @JsonIgnore
    private final Room room;
    @Getter
    private final List<Message> messages = new ArrayList<>();

    public void sendMessage(Player player, String messageText) {
        if (messageText.equals("")) return;
        Message message = new Message(player.getUuid(), messageText);
        this.messages.add(message);
        this.room.executeForPlayers(p -> p.sendMessage(message));
    }

    public void deleteMessage(UUID messageUUID) {
        Iterator<Message> iter = this.messages.iterator();
        while(iter.hasNext()){
            Message next = iter.next();
            if(next.getUuid().equals(messageUUID)){
                iter.remove();
                this.room.executeForPlayers(p -> p.deleteMessage(next.getUuid()));
            }
        }
    }

}

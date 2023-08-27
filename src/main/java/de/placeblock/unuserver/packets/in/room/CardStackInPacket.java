package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CardStackInPacket extends InPacket implements RoomRequiredPacket {
    private final List<Card> cards;

    @Override
    public void onReceive(Player player) {
        player.getRoom().setCardStack(this.cards);
    }
}

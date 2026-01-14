package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.cards.CardDeck;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDeckInPacket extends InPacket implements RoomRequiredPacket {
    private CardDeck cardDeck;

    @Override
    public void onReceive(Player player) {
        if (this.cardDeck.getGroups().size() > 30) return;
        for (CardDeck.CardGroup group : this.cardDeck.getGroups()) {
            if (group.getCards().size() > 80 ||
                group.getAmount() > 100) return;
        }
        player.getRoom().setCardDeck(this.cardDeck);
    }
}

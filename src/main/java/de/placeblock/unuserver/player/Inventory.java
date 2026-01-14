package de.placeblock.unuserver.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Inventory {
    @JsonIgnore
    private final RoundPlayer player;

    private final List<Card<?>> cards = new ArrayList<>();

    public void removeCard(Card<?> card) {
        this.cards.remove(card);
        this.sendPlayersCardAmount();
    }

    public void addCard(Card<?> card) {
        this.cards.add(card);
        this.sendPlayersCardAmount();
    }

    public void addCards(List<Card<?>> cards) {
        this.cards.addAll(cards);
        this.sendPlayersCardAmount();
    }

    public boolean canPlay(Round round, Card<?> card) {
        for (Card<?> invCard : this.cards) {
            if (card.isValidNextCard(round, invCard)) return true;
        }
        return false;
    }

    public Card<?> getCard(UUID uuid) {
        for (Card<?> card : this.cards) {
            if (card.getUuid().equals(uuid)) {
                return card;
            }
        }
        return null;
    }

    public void sendPlayersCardAmount() {
        Room room = this.player.getPlayer().getRoom();
        room.executeForPlayers(p -> p.setPlayerCardAmount(this.player, this.size()));
    }

    public int size() {
        return this.cards.size();
    }

}

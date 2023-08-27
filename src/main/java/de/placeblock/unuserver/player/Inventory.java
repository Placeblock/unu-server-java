package de.placeblock.unuserver.player;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.game.Room;
import de.placeblock.unuserver.game.round.RoundPlayer;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Inventory {
    private final RoundPlayer player;

    private final List<Card> cards = new ArrayList<>();

    public void removeCard(Card card) {
        this.cards.remove(card);
        this.sendPlayersCardAmount();
    }

    public void addCard(Card card) {
        this.cards.add(card);
        this.sendPlayersCardAmount();
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
        this.sendPlayersCardAmount();
    }

    public void sendPlayersCardAmount() {
        Room room = this.player.getPlayer().getRoom();
        room.executeForPlayers(p -> p.setPlayerCardAmount(this.player, this.size()));
    }

    public boolean hasCard(Card card) {
        return this.cards.contains(card);
    }

    public int size() {
        return this.cards.size();
    }

}

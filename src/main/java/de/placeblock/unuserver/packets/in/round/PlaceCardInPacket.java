package de.placeblock.unuserver.packets.in.round;

import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.RoundPlayer;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class PlaceCardInPacket extends InPacket implements RoundRequiredPacket {
    private final Card card;

    @Override
    public void onReceive(Player player) {
        Round round = player.getRoom().getRound();
        RoundPlayer roundPlayer = round.getRoundPlayer(player.getUuid());
        round.placeCard(roundPlayer, this.card);
    }
}

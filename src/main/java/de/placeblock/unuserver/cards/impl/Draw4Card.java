package de.placeblock.unuserver.cards.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import de.placeblock.unuserver.cards.Card;
import de.placeblock.unuserver.cards.Color;
import de.placeblock.unuserver.cards.Colored;
import de.placeblock.unuserver.cards.ForceColorCard;
import de.placeblock.unuserver.game.round.Round;
import de.placeblock.unuserver.game.round.move.Move;
import de.placeblock.unuserver.packets.in.round.SelectColorInPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("draw_4")
public class Draw4Card extends Card<Draw4Card> implements ForceColorCard {
    private Color forceColor;

    @Override
    public boolean isValidNextCard(Round round, Card<?> card) {
        if ((card instanceof Draw4Card && round.getRoundSettings().isPlus4OnPlus4()) ||
                (card instanceof WishCard && round.getRoundSettings().isWishOnPlus4())) return true;
        if (card instanceof Draw2Card && !round.getRoundSettings().isPlus2OnPlus4()) return false;
        return card instanceof Colored colored && this.forceColor == colored.getColor();
    }

    @Override
    public void place(Round round) {
        round.setDrawStack(round.getDrawStack()+4);
        Move currentMove = round.getCurrentMove();
        currentMove.registerPacketHandler(SelectColorInPacket.class, packet -> {
            this.setForceColor(packet.getColor());
            round.getRoom().executeForPlayers(p -> p.setCurrentCard(this));
            round.setNextPlayer(round.calculateNextPlayer());
        });
        currentMove.getRoundPlayer().getPlayer().selectColor();
    }

    @Override
    public boolean canBeginWith() {
        return false;
    }

    @Override
    public Draw4Card copy() {
        return new Draw4Card(this.forceColor);
    }
}

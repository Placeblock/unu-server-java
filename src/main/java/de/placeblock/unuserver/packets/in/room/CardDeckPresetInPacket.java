package de.placeblock.unuserver.packets.in.room;

import de.placeblock.unuserver.cards.stackpresets.CardDeckPreset;
import de.placeblock.unuserver.packets.in.InPacket;
import de.placeblock.unuserver.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDeckPresetInPacket extends InPacket implements RoomRequiredPacket {
    private int id;

    @Override
    public void onReceive(Player player) {
        CardDeckPreset[] values = CardDeckPreset.values();
        if (this.id >= values.length || this.id < 0) return;
        CardDeckPreset preset = values[this.id];
        player.getRoom().setCardDeck(preset.getCardDeck().get());
    }
}

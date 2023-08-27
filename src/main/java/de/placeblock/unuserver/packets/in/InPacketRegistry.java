package de.placeblock.unuserver.packets.in;

import de.placeblock.unuserver.packets.in.player.PlayerNameInPacket;
import de.placeblock.unuserver.packets.in.room.*;
import de.placeblock.unuserver.packets.in.round.*;

import java.util.HashMap;
import java.util.Map;

public class InPacketRegistry {

    private static final Map<String, Class<? extends InPacket>> inPackets = new HashMap<>();

    static {
        inPackets.put("player_name", PlayerNameInPacket.class);
        inPackets.put("card_stack", CardStackInPacket.class);
        inPackets.put("join_room", JoinRoomInPacket.class);
        inPackets.put("kick_room_player", KickRoomPlayerInPacket.class);
        inPackets.put("leave_room", LeaveRoomInPacket.class);
        inPackets.put("round_settings", RoundSettingsInPacket.class);
        inPackets.put("start_round", StartRoundInPacket.class);
        inPackets.put("acknowledge_last_card", AcknowledgeLastCardInPacket.class);
        inPackets.put("draw_card", DrawCardInPacket.class);
        inPackets.put("kick_round_player", KickRoundPlayerInPacket.class);
        inPackets.put("leave_round", LeaveRoundInPacket.class);
        inPackets.put("place_card", PlaceCardInPacket.class);
    }

    public static Class<? extends InPacket> getPacketClass(String action) {
        return inPackets.get(action);
    }

}

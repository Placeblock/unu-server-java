package de.placeblock.unuserver.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PublicRoomInfo {

    private final String code;
    private final String ownerName;
    private final int players;


}

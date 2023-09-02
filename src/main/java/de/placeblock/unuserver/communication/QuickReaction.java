package de.placeblock.unuserver.communication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QuickReaction {

    HAPPY("\uD83D\uDE03"),
    ANGRY("\uD83E\uDD2C"),
    FUNNY("\uD83D\uDE06"),
    TONGUE("\uD83D\uDE1B"),
    SAD("\uD83D\uDE22");

    private final String emoji;

}

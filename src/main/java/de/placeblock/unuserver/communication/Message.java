package de.placeblock.unuserver.communication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Message {
    private final UUID uuid = UUID.randomUUID();
    private final UUID sender;
    private final String content;

}

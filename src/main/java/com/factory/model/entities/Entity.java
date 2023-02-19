package com.factory.model.entities;

import java.util.UUID;

public class Entity {
    private final String ID;

    public Entity() {
        String rawID = UUID.randomUUID().toString();
        String[] rawIDTokens = rawID.split("-");
        ID = rawIDTokens[rawIDTokens.length - 1];
    }

    public String getID() {
        return ID;
    }
}

package net.vincent_clerc.entities;

import java.util.UUID;

public class Entity {

    private UUID id;

    public Entity(UUID uuid) {
        this.id = uuid;
    }

    public UUID getId() {
        return this.id;
    }

}

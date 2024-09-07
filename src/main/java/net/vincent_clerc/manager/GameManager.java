package net.vincent_clerc.manager;

import net.vincent_clerc.entities.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager {

    private Entity currentPlayer;
    private Map<String, Entity> entities;

    public GameManager() {
        this.entities = new ConcurrentHashMap<>();
    }

    public void setCurrentPlayer(Entity entity) {
        this.currentPlayer = entity;
    }

    public Entity getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Map<String, Entity> getEntities() {
        return this.entities;
    }

    public void initialize() {

    }

    public void addEntity(Entity entity) {

        this.entities.put(
                entity.getId().toString(),
                entity
        );

    }

}

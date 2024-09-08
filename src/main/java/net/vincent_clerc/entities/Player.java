package net.vincent_clerc.entities;

import net.vincent_clerc.Game;

import java.util.UUID;

public class Player extends Entity {

    public Player(UUID id, float x, float y, float z) {
        super(id, x, y, z, Game.getInstance().getAssetManager().loadModel("models/ships/starterShip/starterShip.gltf"));
    }

}

package net.vincent_clerc.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.jme3.input.CameraInput;
import com.jme3.input.ChaseCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

import net.vincent_clerc.Game;
import net.vincent_clerc.entities.CurrentPlayer;
import net.vincent_clerc.entities.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager {

    private Game game;
    private Map<String, Entity> entities;
    private CurrentPlayer currentPlayer;

    public GameManager(Game game) {
        this.game = game;
        this.entities = new ConcurrentHashMap<>();
    }

    public void initialize() {
        this.initCamera();
        this.initLight();
    }

    private void initCamera() {

        this.game.getflyCam().setEnabled(false);

        ChaseCamera chaseCam = new ChaseCamera(this.game.getCam(), this.currentPlayer.getMesh(), this.game.getInputManager());
        chaseCam.setDefaultHorizontalRotation(-FastMath.PI/2);
        chaseCam.setDefaultVerticalRotation(FastMath.PI/2);
        chaseCam.setMinDistance(50);
        chaseCam.setMaxDistance(100);

        this.game.getInputManager().deleteMapping(CameraInput.CHASECAM_TOGGLEROTATE);

    }

    private void initLight() {

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.5f, -1.0f, -0.5f).normalizeLocal()); // Direction of the light
        sun.setColor(ColorRGBA.LightGray.mult(0.3f));
        game.getEntities().addLight(sun);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.LightGray.mult(0.2f));
        game.getEntities().addLight(ambient);

    }

    // ---------- CURRENT PLAYER ---------- //

    public CurrentPlayer getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(CurrentPlayer entity) {
            this.currentPlayer = entity;
    }

    // ---------- ENTITIES ---------- //

    public void addEntity(Entity entity) {
        this.entities.put(entity.getId().toString(), entity);
        this.game.addEntity(entity);
    }

    public void updateEntity(Entity entity, JsonNode entityNode) {

        JsonNode position = entityNode.get("position");
        float x = (float) position.get("x").asDouble();
        float y = (float) position.get("y").asDouble();
        float z = (float) position.get("z").asDouble();
        entity.setPosition(x, y, z);

        JsonNode rotation = entityNode.get("rotation");
        float rx = (float) rotation.get("x").asDouble();
        float ry = (float) rotation.get("y").asDouble();
        float rz = (float) rotation.get("z").asDouble();
        entity.setRotation(rx, ry, rz);

    }

    public Map<String, Entity> getEntities() {
        return this.entities;
    }


}

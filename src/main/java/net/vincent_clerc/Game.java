package net.vincent_clerc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.JmeContext;
import net.vincent_clerc.entities.CurrentPlayer;
import net.vincent_clerc.entities.Entity;
import net.vincent_clerc.entities.Player;
import net.vincent_clerc.factories.EntityFactory;
import net.vincent_clerc.input.InputHandler;
import net.vincent_clerc.manager.GameManager;
import net.vincent_clerc.network.NetworkManager;
import net.vincent_clerc.network.message_processor.DataMessageProcessor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Game extends SimpleApplication {

    private static Game instance;

    public static Game getInstance() {

        if(Game.instance == null) {
            Game.instance = new Game();
        }

        return Game.instance;

    }

    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.start();
    }

    private NetworkManager networkManager;
    private GameManager gameManager;
    private ObjectMapper objectMapper;
    private InputHandler inputHandler;

    public Game() {
        this.networkManager = new NetworkManager(this::playerConnectionCallback);
        this.objectMapper = new ObjectMapper();
    }

    private void playerConnectionCallback(String message) throws JsonProcessingException {

        JsonNode rootNode = this.objectMapper.readTree(message);
        String id = rootNode.get("id").textValue();

        CurrentPlayer currentPlayer = new CurrentPlayer(UUID.fromString(id), this.networkManager);

        this.gameManager = new GameManager(this);
        this.gameManager.addEntity(currentPlayer);
        this.gameManager.setCurrentPlayer(currentPlayer);
        this.gameManager.initialize();

        this.inputHandler = new InputHandler(this.inputManager);
        this.inputHandler.init();

        this.networkManager.connectionHandler.messageProcessor = new DataMessageProcessor(this::connectionReadCallback);

    }

    private void connectionReadCallback(String message) throws JsonProcessingException {

        JsonNode rootNode = this.objectMapper.readTree(message);
        JsonNode entitiesNode = rootNode.get("entities");

        Iterator<JsonNode> entitiesIterator = entitiesNode.elements();

        this.enqueue(() -> {
            this.processEntities(entitiesIterator);
        });

    }

    private void processEntities(Iterator<JsonNode> entitiesIterator) {

        Map<String, JsonNode> incomingEntities = new HashMap<>();

        while (entitiesIterator.hasNext()) {

            JsonNode entityNode = entitiesIterator.next();
            String entityId = entityNode.get("id").asText();

            incomingEntities.put(entityId, entityNode);

            if (this.gameManager.getEntities().containsKey(entityId)) {
                Entity existingEntity = this.gameManager.getEntities().get(entityId);
                this.gameManager.updateEntity(existingEntity, entityNode);
            } else {
                EntityFactory entityFactory = new EntityFactory();
                Entity newEntity = entityFactory.build(entityNode);
                this.gameManager.addEntity(newEntity);
            }

        }

        this.gameManager.getEntities().keySet().removeIf(id -> !incomingEntities.containsKey(id));

    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public AssetManager getAssetManager() {
        return this.assetManager;
    }

    public Node getEntities() {
        return this.rootNode;
    }

    public void addEntity(Entity entity) {
        entity.getMesh().setUserData("entity", entity);
        System.out.println(entity.getMesh().getName());
        this.rootNode.attachChild(entity.getMesh());
    }

    public FlyByCamera getflyCam() {
        return this.flyCam;
    }

    public Camera getCam() {
        return this.cam;
    }

    public InputManager getCamgetInputManager() {
        return this.inputManager;
    }

    @Override
    public void start() {
        start(JmeContext.Type.Display, false);
    }

    @Override
    public void simpleInitApp() {
        this.networkManager.initialize();
    }

}
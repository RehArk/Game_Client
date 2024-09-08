package net.vincent_clerc;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.JmeContext;

import net.vincent_clerc.entities.Entity;
import net.vincent_clerc.entities.Player;
import net.vincent_clerc.manager.GameManager;
import net.vincent_clerc.network.NetworkManager;
import net.vincent_clerc.network.message_processor.DataMessageProcessor;

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

    public Game() {

        this.networkManager = new NetworkManager((id) -> {

            Player currentPlayer = new Player(UUID.fromString(id[0].toString()), 0, 0, 0);

            this.gameManager = new GameManager(this);
            this.gameManager.addEntity(currentPlayer);
            this.gameManager.setCurrentPlayer(currentPlayer);
            this.gameManager.initialize();

            this.networkManager.connectionHandler.messageProcessor = new DataMessageProcessor(this::test);

        });

    }

    private void test(Object... objects) {
        System.out.println("Test");
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
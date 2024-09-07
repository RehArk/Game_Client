package net.vincent_clerc;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.JmeContext;
import net.vincent_clerc.entities.Entity;
import net.vincent_clerc.manager.GameManager;
import net.vincent_clerc.network.NetworkManager;
import net.vincent_clerc.network.message_processor.DataMessageProcessor;
import org.lwjgl.Sys;

import java.util.UUID;

public class Game extends SimpleApplication {

    private static Game instance;

    public static Game getInstance() {
        return Game.instance;
    }

    public static void main(String[] args) {
        Game.instance = new Game();
        Game.getInstance().start();
    }

    public Game() {

        this.gameManager = new GameManager();
        this.networkManager = new NetworkManager((id) -> {

            Entity currentPlayer = new Entity(UUID.fromString(id[0].toString()));

            this.gameManager.addEntity(currentPlayer);
            this.gameManager.setCurrentPlayer(currentPlayer);

            this.networkManager.connectionHandler.messageProcessor = new DataMessageProcessor(this::test);

        });

    }

    private void test(Object... objects) {
        System.out.println("Test");
    }

    private NetworkManager networkManager;
    private GameManager gameManager;

    @Override
    public void start() {
        start(JmeContext.Type.Display, false);
    }

    @Override
    public void simpleInitApp() {

        this.networkManager.initialize();

        Box b = new Box(1,1,1); // create cube shape
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
        geom.setMaterial(mat);                   // set the cube's material
        rootNode.attachChild(geom);

    }

}
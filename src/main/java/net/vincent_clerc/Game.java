package net.vincent_clerc;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.JmeContext;
import net.vincent_clerc.network.NetworkManager;

public class Game extends SimpleApplication {

    private static Game instance;

    public static Game getInstance() {
        return Game.instance;
    }

    public static void main(String[] args) {
        Game.instance = new Game();
        Game.getInstance().start();
    }

    private NetworkManager networkManager;

    @Override
    public void start() {
        start(JmeContext.Type.Display, false);
    }

    @Override
    public void simpleInitApp() {

        this.networkManager = new NetworkManager();
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
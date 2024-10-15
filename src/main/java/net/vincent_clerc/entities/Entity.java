package net.vincent_clerc.entities;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import net.vincent_clerc.Game;

import java.util.UUID;

public class Entity implements Savable {

    protected UUID id;

    protected Vector3f position;
    protected Quaternion rotation;

    protected Spatial mesh;

    public Entity(UUID id) {
        this.id = id;

        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Quaternion();

        this.mesh = Game.getInstance().getAssetManager().loadModel("models/asteroid/asteroid.gltf");
    }

    public Entity(UUID uuid, String mesh) {
        this.id = uuid;

        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Quaternion();

        this.mesh = Game.getInstance().getAssetManager().loadModel("models/" + mesh + ".gltf");
    }

    public UUID getId() {
        return this.id;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
        this.mesh.setLocalTranslation(x, y, z);
    }

    public Quaternion getRotation() {
        return this.rotation;
    }

    public void setRotation(float angleX, float angleY, float angleZ) {

        // Create a temporary quaternion for each axis rotation
        Quaternion qx = new Quaternion();
        Quaternion qy = new Quaternion();
        Quaternion qz = new Quaternion();

        // Compute the quaternions for each rotation around X, Y, and Z axes
        qx.fromAngleAxis(angleX, Vector3f.UNIT_X);
        qy.fromAngleAxis(angleY, Vector3f.UNIT_Y);
        qz.fromAngleAxis(angleZ, Vector3f.UNIT_Z);

        // Combine the quaternions to get the final orientation
        this.rotation.set(qz.mult(qy).mult(qx));
        this.mesh.setLocalRotation(rotation);

    }

    public Spatial getMesh() {
        return mesh;
    }

    public void write(JmeExporter var1) {

    };

    public void read(JmeImporter var1) {

    };

}

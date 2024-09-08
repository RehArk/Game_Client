package net.vincent_clerc.entities;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import java.util.UUID;

public class Entity implements Savable {

    private UUID id;
    private Vector3f position;
    private Spatial mesh;

    public Entity(UUID uuid, float x, float y, float z, Spatial mesh) {
        this.id = uuid;
        this.position = new Vector3f(x, y, z);
        this.mesh = mesh;
    }

    public UUID getId() {
        return this.id;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Spatial getMesh() {
        return mesh;
    }

    public void write(JmeExporter var1) {

    };

    public void read(JmeImporter var1) {

    };

}

package net.vincent_clerc.input;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Plane;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import net.vincent_clerc.Game;
import net.vincent_clerc.entities.Entity;

public class InputHandler {

    private InputManager inputManager;

    public InputHandler(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public void init () {
        initMouse();
        initKeys();
    }

    private void initMouse() {

        inputManager.addMapping("RightClick", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("LeftClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        inputManager.addListener(actionListener, "RightClick", "LeftClick");

    }

    private void initKeys() {

        inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addMapping("MoveBackward", new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(actionListener, "MoveLeft", "MoveRight", "MoveForward", "MoveBackward");

    }

    private final ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {

            if (name.equals("RightClick") && isPressed) {
                System.out.println("right click");
                handleRightClick();
            }

            if (name.equals("LeftClick") && isPressed) {
                System.out.println("left click");
                handleLeftClick();
            }

            if (isPressed) {
                // handle key pressing
            }

        }
    };

    private Ray getRay() {

        // Obtenir les coordonnées du curseur de la souris
        Vector2f click2d = inputManager.getCursorPosition();

        // Convertir les coordonnées 2D en un point 3D sur le near plane et far plane de la caméra
        Vector3f click3d_near = Game.getInstance().getCam().getWorldCoordinates(new Vector2f(click2d.getX(), click2d.getY()), 0f);
        Vector3f click3d_far = Game.getInstance().getCam().getWorldCoordinates(new Vector2f(click2d.getX(), click2d.getY()), 1f);

        // Calculer la direction du rayon
        Vector3f direction = click3d_far.subtract(click3d_near).normalizeLocal();

        // Créer le rayon
        return new Ray(click3d_near, direction);

    }

    private void handleRightClick() {

        Ray ray = this.getRay();

        CollisionResults results = new CollisionResults();
        Game.getInstance().getRootNode().collideWith(ray, results);

        if (results.size() > 0) {

            Spatial target = results.getClosestCollision().getGeometry();

            while(target.getParent() != null && !target.getParent().getName().equals("Root Node")){
                target = target.getParent();
                System.out.println(target.getName());
            }

            Entity entity = target.getUserData("entity");

            if (entity == null) {
                return;
            }

            System.out.println("Vous avez cliqué sur : " + entity.getId() + " " + entity.getClass().getSimpleName());

//            Game.getInstance().getGameManager().getCurrentPlayer()
//                    .attack(
//                            Game.getInstance().getCurrentPlayer(),
//                            entity
//                    );

        } else {
            System.out.println("Aucun modèle touché.");
        }


    }

    private void handleLeftClick() {

        // Créer le rayon
        Ray ray = this.getRay();

        // Définir le plan (par exemple, plan horizontal à Y=0)
        Plane plane = new Plane(Vector3f.UNIT_Y, 0);

        // Calculer l'intersection entre le rayon et le plan
        Vector3f intersection = new Vector3f();
        CollisionResult result = new CollisionResult();

        if (!ray.intersectsWherePlane(plane, intersection)) {
            return;
        }

        System.out.println("Point d'intersection sur le plan : " + intersection);

        Game.getInstance().getGameManager().getCurrentPlayer().move(intersection);

    }

}
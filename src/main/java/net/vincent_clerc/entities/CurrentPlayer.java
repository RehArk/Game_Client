package net.vincent_clerc.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.math.Vector3f;
import net.vincent_clerc.network.NetworkManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CurrentPlayer extends Player {

    private NetworkManager networkManager;

    public CurrentPlayer(UUID id,  NetworkManager networkManager) {
        super(id);
        this.networkManager = networkManager;
    }

    public void attack(Entity entity) {
        // Logique d'attaque
    }

    public void move(Vector3f targetedPosition) {

        System.out.println(targetedPosition);

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> action_data = new HashMap<>();
        action_data.put("action", "move");
        action_data.put("x", targetedPosition.getX());
        action_data.put("z", targetedPosition.getZ());

        try {
            String json = mapper.writeValueAsString(action_data);
            System.out.println(json);
            this.networkManager.sendMessage(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

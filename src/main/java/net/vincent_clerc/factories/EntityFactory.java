package net.vincent_clerc.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.vincent_clerc.entities.Entity;
import net.vincent_clerc.entities.Player;
import org.lwjgl.Sys;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityFactory {

    Map<String, Class> entityType;

    public EntityFactory() {
        this.entityType = new HashMap<>();
        this.entityType.put("Entity", Entity.class);
        this.entityType.put("Player", Player.class);
        this.entityType.put("Asteroid", Entity.class);
    }

    public Entity build(JsonNode entityNode) {

        ObjectMapper objectMapper = new ObjectMapper();
        String prettyJson = null;

        try {
            prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entityNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prettyJson);

        UUID id = UUID.fromString(entityNode.get("id").asText());
        String type = entityNode.get("type").asText();

        Class<? extends Entity> entityClass = entityType.get(type);

        Entity entity = null;

        try {
            entity = entityClass.getConstructor(UUID.class).newInstance(id);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return entity;

    }

}

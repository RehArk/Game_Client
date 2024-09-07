package net.vincent_clerc.network.message_processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.vincent_clerc.utils.Callback;

public class InitialMessageProcessor extends MessageProcessor {

    public void process(Callback callback, String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(message);
        String id = rootNode.get("id").textValue();

        try {
            callback.call(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

}

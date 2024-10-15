package net.vincent_clerc.network.message_processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.vincent_clerc.utils.callbacks.PlayerConnectionCallback;

public class InitialMessageProcessor extends MessageProcessor {

    protected PlayerConnectionCallback playerConnectionCallback;

    public InitialMessageProcessor(PlayerConnectionCallback playerConnectionCallback) {
        this.playerConnectionCallback = playerConnectionCallback;
    }

    @Override
    public void process(String message) {

        try {
            this.playerConnectionCallback.call(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

}

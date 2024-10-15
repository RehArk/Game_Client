package net.vincent_clerc.network.message_processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.vincent_clerc.utils.callbacks.ConnectionReadCallback;

public class DataMessageProcessor extends MessageProcessor {

    protected ConnectionReadCallback playerConnectionCallback;

    public DataMessageProcessor(ConnectionReadCallback playerConnectionCallback) {
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

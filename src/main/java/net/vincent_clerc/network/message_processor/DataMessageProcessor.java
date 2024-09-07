package net.vincent_clerc.network.message_processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.vincent_clerc.utils.Callback;

public class DataMessageProcessor extends MessageProcessor {
    
    public DataMessageProcessor(Callback callback) {
        super(callback);
    }

    @Override
    public void process(String message) throws JsonProcessingException {

    }
}

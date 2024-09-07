package net.vincent_clerc.network.message_processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.vincent_clerc.utils.Callback;

public abstract class MessageProcessor {

    protected  Callback callback;

    public MessageProcessor(Callback callback) {
        this.callback = callback;
    }

    public abstract void process(String message) throws JsonProcessingException;

}

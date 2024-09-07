package net.vincent_clerc.network.message_processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.vincent_clerc.utils.Callback;

public abstract class MessageProcessor {

    public abstract void process(Callback callback, String message) throws JsonProcessingException;

}

package net.vincent_clerc.network.message_processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.vincent_clerc.utils.callbacks.PlayerConnectionCallback;

public abstract class MessageProcessor {

    public abstract void process(String message);

}

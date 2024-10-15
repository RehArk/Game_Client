package net.vincent_clerc.utils.callbacks;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface ConnectionReadCallback<T> {
    void call(String result) throws JsonProcessingException;
}
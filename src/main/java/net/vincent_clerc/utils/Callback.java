package net.vincent_clerc.utils;

@FunctionalInterface
public interface Callback<T> {
    void call(T result);
}
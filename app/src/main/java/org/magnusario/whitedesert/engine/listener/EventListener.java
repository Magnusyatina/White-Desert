package org.magnusario.whitedesert.engine.listener;

public interface EventListener<T> {
    void handle(T event);

    Class<T> getHandledObjectClass();
}

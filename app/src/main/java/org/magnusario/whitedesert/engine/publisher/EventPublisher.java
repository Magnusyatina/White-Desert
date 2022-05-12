package org.magnusario.whitedesert.engine.publisher;

import org.magnusario.whitedesert.engine.listener.EventListener;

public interface EventPublisher {

    <T> void publish(T event);

    <T> void register(Class<T> eventClass, EventListener<T> eventListener);
}

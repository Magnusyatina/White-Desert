package org.magnusario.whitedesert.engine.publisher;

import org.magnusario.whitedesert.engine.listener.EventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EventPublisherImpl implements EventPublisher {

    private Map<Class<?>, EventListener<?>> mappedEventListeners = new HashMap<>();

    @Inject
    public EventPublisherImpl(Set<EventListener<?>> eventListeners) {
        for (EventListener<?> eventListener : eventListeners) {
            register(eventListener.getHandledObjectClass(), (EventListener) eventListener);
        }
    }

    @Override
    public <T> void register(Class<T> eventClass, EventListener<T> eventListener) {
        mappedEventListeners.put(eventClass, eventListener);
    }

    @Override
    public <T> void publish(T event) {
        Class<T> eventClass = (Class<T>) event.getClass();
        EventListener<T> eventListener = (EventListener<T>) mappedEventListeners.get(eventClass);
        if (eventListener != null)
            eventListener.handle(event);
    }
}

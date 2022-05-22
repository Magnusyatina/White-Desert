package org.magnusario.whitedesert.engine;

import org.magnusario.whitedesert.engine.event.IEvent;

public interface EventPool {

    void submit(IEvent event);

    void submit(IEvent event, long delay);

    void submit(Runnable runnable);

    void stopAll();
}

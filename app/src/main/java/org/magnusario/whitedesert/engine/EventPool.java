package org.magnusario.whitedesert.engine;

import org.magnusario.whitedesert.engine.event.IEvent;

public interface EventPool {

    void notify(IEvent event);

    void notify(IEvent event, long delay);

    void notify(Runnable runnable);

    void stopAll();
}

package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

import java.util.Properties;

/**
 * Created by sergey37192 on 04.04.2018.
 */

public interface IEvent {
    void start(EventObserver eventObserver);

    void add(IEvent event);

    void serProperties(Properties properties);
}

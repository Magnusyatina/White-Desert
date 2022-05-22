package org.magnusario.whitedesert.engine.event;

import java.util.Properties;

public interface IEvent {

    void add(IEvent event);

    void serProperties(Properties properties);
}

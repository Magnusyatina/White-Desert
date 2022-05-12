package org.magnusario.whitedesert.engine.event;

import java.util.Properties;

/**
 * Created by sergey37192 on 04.04.2018.
 */

public interface IEvent {

    void add(IEvent event);

    void serProperties(Properties properties);
}

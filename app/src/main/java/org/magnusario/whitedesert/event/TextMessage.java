package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class TextMessage extends Messages {

    private static final long serialVersionUID = -5578033749054499832L;

    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }
}

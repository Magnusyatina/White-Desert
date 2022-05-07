package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

/**
 * Created by sergey37192 on 05.04.2018.
 */

public class StartNewGame extends Event {
    private static final long serialVersionUID = 8722565699314515608L;

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }
}

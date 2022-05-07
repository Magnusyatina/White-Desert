package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

/**
 * Created by sergey37192 on 04.04.2018.
 */

public class StartGame extends Event {
    private static final long serialVersionUID = -2465351716310442057L;

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }
}

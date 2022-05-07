package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

/**
 * Created by sergey37192 on 22.02.2018.
 */

public class PlayerAnswer extends Messages {
    private static final long serialVersionUID = -2943686592703973330L;

    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }
}

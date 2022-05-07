package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class Die extends Event {
    private static final long serialVersionUID = 2109187804863111723L;

    public void start(EventObserver eventObserver) {
        long time = getTimer();
        eventObserver.onEvent(this);
    }
}

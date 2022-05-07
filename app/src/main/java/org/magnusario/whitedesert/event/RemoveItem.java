package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class RemoveItem extends Event {
    private static final long serialVersionUID = 2685011772135588599L;
    Integer item;

    public void start(EventObserver eventObserver) {
        if (!added) {
            long time = getTimer();
            eventObserver.onEvent(this);

        }

    }

    public void setItem(int i) {
        this.item = i;
    }

    public Integer getItem() {
        return item;
    }
}

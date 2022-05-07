package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class AddItem extends Event {
    private static final long serialVersionUID = -808196659193899191L;
    int itemId;

    public void setItem(int i) {
        this.itemId = i;
    }

    public void start(EventObserver eventObserver) {
        long time = getTimer();
        eventObserver.onEvent(this);
    }

    public Integer getItem() {
        return itemId;
    }
}

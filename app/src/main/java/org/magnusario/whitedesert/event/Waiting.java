package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

/**
 * Created by sergey37192 on 13.02.2018.
 */

public class Waiting extends Event {

    private static final long serialVersionUID = -7545553894402138022L;
    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

}

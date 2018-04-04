package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

/**
 * Created by sergey37192 on 05.04.2018.
 */

public class StartNewGame implements Event {
    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }
}

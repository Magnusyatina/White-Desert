package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class Die extends CustomEvents {
    public void start(EventObserver eventObserver){
        long time = getTimer();
        eventObserver.onEvent( this);

    }
}

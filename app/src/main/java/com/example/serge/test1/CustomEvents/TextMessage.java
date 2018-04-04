package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class TextMessage extends Messages {

    public void start(EventObserver eventObserver){
        long time = getTimer();
        eventObserver.onEvent( this );

    }
}

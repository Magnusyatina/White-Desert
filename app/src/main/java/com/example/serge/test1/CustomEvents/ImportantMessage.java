package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class ImportantMessage extends Messages {
    public void start(EventObserver eventObserver){
        eventObserver.onEvent( this );
    }

}

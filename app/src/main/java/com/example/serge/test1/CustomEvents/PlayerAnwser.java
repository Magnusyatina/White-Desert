package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

/**
 * Created by sergey37192 on 22.02.2018.
 */

public class PlayerAnwser extends Messages {
    public void start(EventObserver eventObserver){
        eventObserver.onEvent( this );
    }
}

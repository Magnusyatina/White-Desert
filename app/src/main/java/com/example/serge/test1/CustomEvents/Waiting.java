package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

/**
 * Created by sergey37192 on 13.02.2018.
 */

public class Waiting extends Event {

    private int value;

    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public void start(EventObserver eventObserver){
        eventObserver.onEvent( this );
    }

}

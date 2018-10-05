package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class RemoveItem extends Event {
    Integer item;

    public void start(EventObserver eventObserver){
        if(!added){
            long time = getTimer();
            eventObserver.onEvent( this);

        }

    }

    public void setItem(int i){
        this.item = i;
    }

    public Integer getItem(){
        return item;
    }
}

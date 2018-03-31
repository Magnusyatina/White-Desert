package com.example.serge.test1.Objects;

import com.example.serge.test1.MainActivity;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class RemoveItem extends CustomEvents{
    Integer item;

    public void start(MainActivity activity){
        if(!added){
            long time = getTimer();
            activity.onEvent( this, time );

        }

    }

    public void setItem(int i){
        this.item = i;
    }

    public Integer getItem(){
        return item;
    }
}

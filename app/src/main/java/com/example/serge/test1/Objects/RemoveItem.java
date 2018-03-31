package com.example.serge.test1.Objects;

import com.example.serge.test1.Engine;
import com.example.serge.test1.MainActivity;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class RemoveItem extends CustomEvents{
    Integer item;

    public void start(Engine engine){
        if(!added){
            long time = getTimer();
            engine.onEvent( this);

        }

    }

    public void setItem(int i){
        this.item = i;
    }

    public Integer getItem(){
        return item;
    }
}

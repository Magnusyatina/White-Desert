package com.example.serge.test1.Objects;

import com.example.serge.test1.MainActivity;

/**
 * Created by sergey37192 on 13.02.2018.
 */

public class Waiting extends  CustomEvents {

    private int value;

    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public void start(MainActivity activity){
        long time = getTimer();
        activity.onEvent( this, time );

    }

}

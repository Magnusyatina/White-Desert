package com.example.serge.test1.Objects;

import com.example.serge.test1.MainActivity;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class Die extends CustomEvents {
    public void start(MainActivity activity){
        long time = getTimer();
        activity.onEvent( this, time );

    }
}

package com.example.serge.test1.Objects;

import com.example.serge.test1.Engine;
import com.example.serge.test1.MainActivity;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class Die extends CustomEvents {
    public void start(Engine engine){
        long time = getTimer();
        engine.onEvent( this);

    }
}

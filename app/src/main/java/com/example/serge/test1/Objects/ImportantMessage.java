package com.example.serge.test1.Objects;

import com.example.serge.test1.Engine;
import com.example.serge.test1.MainActivity;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class ImportantMessage extends Messages {
    public void start(Engine engine){
        engine.onEvent( this );
    }

}

package com.example.serge.test1.Objects;

import com.example.serge.test1.MainActivity;

/**
 * Created by sergey37192 on 22.02.2018.
 */

public class PlayerAnwser extends Messages {
    public void start(MainActivity activity){
        activity.onEvent( this );
    }
}

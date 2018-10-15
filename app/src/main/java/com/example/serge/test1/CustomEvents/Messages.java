package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;
import com.example.serge.test1.MainActivity;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class Messages extends Event {
    private String text;


    public Integer addItemId;
    public Integer removeItemId;

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }


    @Override
    public void start(EventObserver eventObserver) {

    }
}

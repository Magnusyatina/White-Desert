package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

public class Hint extends Event {

    private String text = null;

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}

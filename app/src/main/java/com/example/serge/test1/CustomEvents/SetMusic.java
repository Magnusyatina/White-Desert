package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

public class SetMusic extends Event {

    private boolean enable = true;

    public void start(EventObserver eventObserver){
        eventObserver.onEvent(this);
    }

    public void setEnable(boolean enable ){
        this.enable = enable;
    }

    public boolean isEnable(){
        return enable;
    }
}

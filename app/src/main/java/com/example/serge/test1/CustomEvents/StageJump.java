package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

public class StageJump extends CustomEvents{

    private String target = null;

    @Override
    public void start(EventObserver eventObserver) {

        eventObserver.onEvent(this);
    }

    public void setTarget(String target){
        this.target = target;
    }

    public String getStage(){
        return target;
    }
}

package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

public class SetGameMode extends Event {

    private boolean fast_game = false;

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    public void setFast_game(boolean b){
        this.fast_game = b;
    }

    public boolean getFast_game(){
        return fast_game;
    }
}

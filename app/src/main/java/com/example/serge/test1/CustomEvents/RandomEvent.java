package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

import java.util.Random;

/**
 * Created by sergey37192 on 13.02.2018.
 */

public class RandomEvent extends Event {
    private String target;
    private int coef = 15;

    public RandomEvent(int coef){
        this.coef = coef;
    }

    public RandomEvent(){}

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

    public boolean check(){
        Random rand = new Random();
        int i = rand.nextInt( 100 );
        if(i>coef)
            return true;
        else return false;
    }
}

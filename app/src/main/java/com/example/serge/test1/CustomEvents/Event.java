package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

import java.io.Serializable;

/**
 * Created by sergey37192 on 12.02.2018.
 */

public class Event implements Serializable, Cloneable, IEvent {
    protected long scheduledtime;
    protected boolean added = false;
    private String stage;

    public void setScheduledtime(long scheduledtime){
        this.scheduledtime = scheduledtime;
    }
    public void setAdded(boolean added){
        this.added = added;
    }


    public void start(EventObserver eventObserver){
        eventObserver.onEvent(this);
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public long getTimer(){
        long currentTime = System.currentTimeMillis();
        return (scheduledtime - currentTime);
    }

    public void setStage(String stage){
        this.stage = stage;
    }

    public String getStage(){
        return stage;
    }

    public long getScheduledtime(){
        return scheduledtime;
    }
    public boolean getAdded(){
        return added;
    }
}

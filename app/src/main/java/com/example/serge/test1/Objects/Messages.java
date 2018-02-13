package com.example.serge.test1.Objects;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class Messages extends CustomEvents{
    private int scheduledtime;
    private boolean added = false;

    public void setScheduledtime(int scheduledtime){
        this.scheduledtime = scheduledtime;
    }
    public void setAdded(boolean added){
        this.added = added;
    }

    public int getScheduledtime(){
        return scheduledtime;
    }
    public boolean getAdded(){
        return added;
    }
}

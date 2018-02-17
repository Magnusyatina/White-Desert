package com.example.serge.test1.Objects;

/**
 * Created by sergey37192 on 12.02.2018.
 */

public class CustomEvents {
    private long scheduledtime;
    private boolean added = false;

    public void setScheduledtime(long scheduledtime){
        this.scheduledtime = scheduledtime;
    }
    public void setAdded(boolean added){
        this.added = added;
    }

    public long getScheduledtime(){
        return scheduledtime;
    }
    public boolean getAdded(){
        return added;
    }
}

package com.example.serge.test1.Objects.Contains;

import java.io.Serializable;

/**
 * Created by sergey37192 on 12.02.2018.
 */

public class CustomEvents implements Serializable, Cloneable{
    private long scheduledtime;
    private boolean added = false;

    public void setScheduledtime(long scheduledtime){
        this.scheduledtime = scheduledtime;
    }
    public void setAdded(boolean added){
        this.added = added;
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public long getScheduledtime(){
        return scheduledtime;
    }
    public boolean getAdded(){
        return added;
    }
}

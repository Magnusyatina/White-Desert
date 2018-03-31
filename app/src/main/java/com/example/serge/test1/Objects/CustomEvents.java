package com.example.serge.test1.Objects;

import com.example.serge.test1.Engine;
import com.example.serge.test1.MainActivity;

import java.io.Serializable;

/**
 * Created by sergey37192 on 12.02.2018.
 */

public class CustomEvents implements Serializable, Cloneable{
    protected long scheduledtime;
    protected boolean added = false;

    public void setScheduledtime(long scheduledtime){
        this.scheduledtime = scheduledtime;
    }
    public void setAdded(boolean added){
        this.added = added;
    }


    public void start(Engine engine){
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public long getTimer(){
        long currentTime = System.currentTimeMillis();
        return (scheduledtime - currentTime);
    }

    public long getScheduledtime(){
        return scheduledtime;
    }
    public boolean getAdded(){
        return added;
    }
}

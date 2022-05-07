package org.magnusario.whitedesert.event;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by sergey37192 on 12.02.2018.
 */

public abstract class Event implements Serializable, Cloneable, IEvent {
    protected long scheduledtime;
    protected boolean added = false;
    private String stage;
    private LinkedList<IEvent> subEvents = null;
    //Testing field. The implementation is created
    private Properties properties = null;

    public void add(IEvent event) {
        if (subEvents == null)
            subEvents = new LinkedList<>();
        subEvents.add(event);
    }

    @Override
    public void serProperties(Properties properties) {
        this.properties = properties;
    }

    public void setScheduledtime(long scheduledtime) {
        this.scheduledtime = scheduledtime;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public long getTimer() {
        long currentTime = System.currentTimeMillis();
        return (scheduledtime - currentTime);
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStage() {
        return stage;
    }

    public long getScheduledtime() {
        return scheduledtime;
    }

    public boolean getAdded() {
        return added;
    }
}

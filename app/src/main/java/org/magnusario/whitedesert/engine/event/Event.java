package org.magnusario.whitedesert.engine.event;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Properties;

public abstract class Event implements Serializable, Cloneable, IEvent {
    protected long scheduledtime = 0;
    protected boolean handled = false;
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

    public void setHandled(boolean handled) {
        this.handled = handled;
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

    public boolean isHandled() {
        return handled;
    }
}

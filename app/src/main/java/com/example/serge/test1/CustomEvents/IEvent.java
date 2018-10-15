package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

import java.util.Properties;

/**
 * Created by sergey37192 on 04.04.2018.
 */

public interface IEvent {
    public void start(EventObserver eventObserver);

    public void add(IEvent event);

    public String getPropVal(String propName);
    
    public void serProperties(Properties properties);
    
    public Properties getProperties();
}

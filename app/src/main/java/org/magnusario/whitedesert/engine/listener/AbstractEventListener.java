package org.magnusario.whitedesert.engine.listener;

import android.app.Activity;
import android.content.Context;

import org.magnusario.whitedesert.engine.EventPool;
import org.magnusario.whitedesert.view.ViewManager;

import javax.inject.Inject;

import dagger.Lazy;

public abstract class AbstractEventListener<T> implements EventListener<T> {

    @Inject
    public Lazy<EventPool> eventPool;

    @Inject
    public Activity activity;

    @Inject
    public Context context;

    @Inject
    public ViewManager viewManager;

    public EventPool getEventPool() {
        return eventPool.get();
    }

    public Activity getActivity() {
        return activity;
    }

    public Context getContext() {
        return context;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }
}

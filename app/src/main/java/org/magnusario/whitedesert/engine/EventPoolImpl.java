package org.magnusario.whitedesert.engine;

import android.os.Handler;
import android.os.Looper;

import org.magnusario.whitedesert.engine.event.IEvent;
import org.magnusario.whitedesert.engine.publisher.EventPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sergey37192 on 01.04.2018.
 */


@Singleton
public class EventPoolImpl implements EventPool {

    @Inject
    public EventPublisher eventPublisher;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Inject
    public EventPoolImpl() {
    }

    @Override
    public void notify(IEvent event) {
        notify(event, 0);
    }

    @Override
    public void notify(final IEvent event, long delay) {
        if (eventPublisher != null)
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    eventPublisher.publish(event);
                }
            }, delay);
    }

    @Override
    public void notify(Runnable runnable) {
        mHandler.post(runnable);
    }

    @Override
    public void stopAll() {
        mHandler.removeCallbacksAndMessages(null);
    }

}

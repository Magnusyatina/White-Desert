package org.magnusario.whitedesert.engine;

import android.os.Handler;
import android.os.Looper;

import org.magnusario.whitedesert.engine.event.IEvent;
import org.magnusario.whitedesert.engine.publisher.EventPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EventPoolImpl implements EventPool {

    @Inject
    public EventPublisher eventPublisher;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Inject
    public EventPoolImpl() {
    }

    @Override
    public void submit(IEvent event) {
        submit(event, 0);
    }

    @Override
    public void submit(final IEvent event, long delay) {
        if (eventPublisher != null)
            mHandler.postDelayed(() -> eventPublisher.publish(event), delay);
    }

    @Override
    public void submit(Runnable runnable) {
        mHandler.post(runnable);
    }

    @Override
    public void stopAll() {
        mHandler.removeCallbacksAndMessages(null);
    }

}

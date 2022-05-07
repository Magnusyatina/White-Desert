package org.magnusario.whitedesert;

import android.os.Handler;
import android.os.Looper;

import org.magnusario.whitedesert.event.IEvent;

/**
 * Created by sergey37192 on 01.04.2018.
 */

public class EventPool {
    //Пул событий
    private EventObserver eventObserver;
    private Handler mHandler = new Handler(Looper.getMainLooper());


    public void onBind(EventObserver eventObserver) {
        this.eventObserver = eventObserver;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void notify(IEvent event) {
       /* if(eventObserver !=null){
            event.start( eventObserver );
        }*/
        notify(event, 0);

    }

    public void notify(final IEvent event, long delay) {
        if (eventObserver != null)
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    event.start(eventObserver);
                }
            }, delay);
    }

    public void notify(Runnable runnable) {
        mHandler.post(runnable);
    }

    public void stopAll() {
        mHandler.removeCallbacksAndMessages(null);
    }


}

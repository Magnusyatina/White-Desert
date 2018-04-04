package com.example.serge.test1;

import android.os.Handler;
import android.os.Looper;

import com.example.serge.test1.CustomEvents.CustomEvents;
import com.example.serge.test1.CustomEvents.Event;

/**
 * Created by sergey37192 on 01.04.2018.
 */

public class EventPool {
    //Пул событий
    private EventObserver eventObserver;
    private Handler mHandler = new Handler( Looper.getMainLooper());


    public void onBind(EventObserver eventObserver){
        this.eventObserver = eventObserver;
    }

    public void notify(Event event){
        if(eventObserver !=null){
            event.start( eventObserver );
        }
    }

    public void notify(final Event event, long delay){
        mHandler.postDelayed( new Runnable() {
            @Override
            public void run() {
                EventPool.this.notify( event );
            }
        }, delay );
    }

    public void stopAll(){
        mHandler.removeCallbacksAndMessages( null );
    }



}

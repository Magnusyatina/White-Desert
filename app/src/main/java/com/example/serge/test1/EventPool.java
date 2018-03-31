package com.example.serge.test1;

import android.os.Handler;
import android.os.Looper;

import com.example.serge.test1.Objects.CustomEvents;

/**
 * Created by sergey37192 on 01.04.2018.
 */

public class EventPool {
    //Пул событий
    private Engine engine;
    private Handler mHandler = new Handler( Looper.getMainLooper());

    public void onBind(Engine engine){
        this.engine = engine;
    }

    public void notify(CustomEvents customEvents){
        if(engine!=null){
            customEvents.start( engine );
        }
    }

    public void notify(final CustomEvents customEvents, long delay){
        mHandler.postDelayed( new Runnable() {
            @Override
            public void run() {
                EventPool.this.notify( customEvents );
            }
        },delay );
    }

}

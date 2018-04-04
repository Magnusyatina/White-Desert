package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

import java.util.ArrayList;

/**
 * Created by sergey37192 on 18.02.2018.
 */

public class Questions extends CustomEvents {
    private ArrayList<Question> list;

    public void put(Question q){
        if(q!=null) {
            if(list == null)
                list = new ArrayList<>();
            list.add( q );
        }
    }

    public void start(EventObserver eventObserver){
        if(!added){
            long time = getTimer();
            eventObserver.onEvent( this );

        }

    }

    public ArrayList<Question> getList(){
        return  list;
    }
}

package com.example.serge.test1.Objects;

import com.example.serge.test1.MainActivity;

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

    public void start(MainActivity activity){
        if(!added){
            long time = getTimer();
            activity.onEvent( this, time );

        }

    }

    public ArrayList<Question> getList(){
        return  list;
    }
}

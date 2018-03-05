package com.example.serge.test1.Objects.Contains;

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

    public ArrayList<Question> getList(){
        return  list;
    }
}

package com.example.serge.test1.Objects;

/**
 * Created by sergey37192 on 13.02.2018.
 */

public class Waiting extends  CustomEvents {

    private long value;

    public void setValue(long value){
        this.value = value;
    }

    public long getValue(){
        return value;
    }

}

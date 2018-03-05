package com.example.serge.test1.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sergey37192 on 05.03.2018.
 */

public class Stage implements Serializable {
    private ArrayList<CustomEvents> array = null;
    private String stage_name = null;
    private boolean reintent = false;



    public void setArray(ArrayList<CustomEvents> array){
        this.array = array;
    }

    public ArrayList<CustomEvents> getArray(){
        return  array;
    }

    public void setStage_name(String stage_name){
        this.stage_name = stage_name;
    }

    public String getStage_name(){
        return stage_name;
    }

    public void setReintent(boolean torf){
        reintent = torf;
    }

    public boolean getReintent(){
        return  reintent;
    }
}

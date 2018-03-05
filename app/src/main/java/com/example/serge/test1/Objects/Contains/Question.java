package com.example.serge.test1.Objects.Contains;

import java.io.Serializable;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class Question implements Serializable{

    private String text;

    private String goTo;
    private String alterGoTo;

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return  text;
    }

    public void setGoTo(String goTo){
        this.goTo = goTo;
    }

    public String getGoTo(){
        return goTo;
    }

    public void setAlterGoTo(String alterGoTo){
        this.alterGoTo = alterGoTo;
    }

    public String getAlterGoTo(){
        return alterGoTo;
    }


    public boolean isAlterGoTo(){
        if(alterGoTo != null)
            return true;
        else return false;
    }

}

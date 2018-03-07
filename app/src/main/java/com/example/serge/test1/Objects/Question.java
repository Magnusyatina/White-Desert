package com.example.serge.test1.Objects;

import java.io.Serializable;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class Question implements Serializable{

    private String text;

    private int needItem = -1;

    private String goTo;
    private String alterGoTo;

    public void setNeedItem(int needItem){
        this.needItem = needItem;
    }

    public int getNeedItem(){
        return needItem;
    }

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

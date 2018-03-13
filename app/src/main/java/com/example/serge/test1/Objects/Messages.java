package com.example.serge.test1.Objects;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class Messages extends CustomEvents{
    private String text;


    public Integer addItemId;
    public Integer removeItemId;

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    

}

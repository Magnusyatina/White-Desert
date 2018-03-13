package com.example.serge.test1.Objects;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class AddItem extends CustomEvents {
    int itemId;

    public void setItem(int i){
        this.itemId = i;
    }

    public Integer getItem(){
        return itemId;
    }
}

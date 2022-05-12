package org.magnusario.whitedesert.engine.event;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class Messages extends Event {
    private static final long serialVersionUID = 4391552054519311744L;
    private String text;


    public Integer addItemId;
    public Integer removeItemId;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}

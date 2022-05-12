package org.magnusario.whitedesert.engine.event;

/**
 * Created by sergey37192 on 13.03.2018.
 */

public class RemoveItem extends Event {
    private static final long serialVersionUID = 2685011772135588599L;
    Integer item;

    public void setItem(int i) {
        this.item = i;
    }

    public Integer getItem() {
        return item;
    }
}

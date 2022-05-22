package org.magnusario.whitedesert.engine.event;

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

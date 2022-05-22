package org.magnusario.whitedesert.engine.event;

public class AddItem extends Event {
    private static final long serialVersionUID = -808196659193899191L;
    int itemId;

    public void setItem(int i) {
        this.itemId = i;
    }

    public Integer getItem() {
        return itemId;
    }
}

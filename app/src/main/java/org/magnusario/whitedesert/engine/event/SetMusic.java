package org.magnusario.whitedesert.engine.event;

public class SetMusic extends Event {

    private static final long serialVersionUID = -9098165394330642421L;
    private boolean enable = true;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }
}

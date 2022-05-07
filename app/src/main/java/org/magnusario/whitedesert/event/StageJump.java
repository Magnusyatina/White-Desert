package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

public class StageJump extends Event {

    private static final long serialVersionUID = 1454249252363624791L;
    private String target = null;

    @Override
    public void start(EventObserver eventObserver) {

        eventObserver.onEvent(this);
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStage() {
        return target;
    }
}

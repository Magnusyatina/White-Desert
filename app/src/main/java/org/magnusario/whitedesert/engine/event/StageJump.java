package org.magnusario.whitedesert.engine.event;

public class StageJump extends Event {

    private static final long serialVersionUID = 1454249252363624791L;
    private String target = null;

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStage() {
        return target;
    }
}

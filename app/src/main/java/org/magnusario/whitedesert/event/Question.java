package org.magnusario.whitedesert.event;

import java.io.Serializable;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class Question implements Serializable {

    private static final long serialVersionUID = -8696294521478159372L;
    private String text;

    private String target;

    private String alterTarget;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setAlterTarget(String alterTarget) {
        this.alterTarget = alterTarget;
    }

    public String getAlterGoTo() {
        return alterTarget;
    }


    public boolean getAlterTarget() {
        if (alterTarget != null)
            return true;
        else return false;
    }

}

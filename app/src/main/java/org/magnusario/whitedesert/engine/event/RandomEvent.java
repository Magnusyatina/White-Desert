package org.magnusario.whitedesert.engine.event;

import java.util.Random;

public class RandomEvent extends Event {
    private static final long serialVersionUID = -241411545137503021L;
    private String target;
    private int coef = 15;

    public RandomEvent(int coef) {
        this.coef = coef;
    }

    public RandomEvent() {
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStage() {
        return target;
    }

    public boolean check() {
        Random rand = new Random();
        int i = rand.nextInt(100);
        if (i > coef)
            return true;
        else return false;
    }
}

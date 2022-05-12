package org.magnusario.whitedesert.engine.event;

public class SetGameMode extends Event {

    private static final long serialVersionUID = -1862900122292483165L;
    private boolean fast_game = false;

    public void setFast_game(boolean b) {
        this.fast_game = b;
    }

    public boolean getFast_game() {
        return fast_game;
    }
}

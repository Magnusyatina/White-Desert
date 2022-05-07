package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

public class SetGameMode extends Event {

    private static final long serialVersionUID = -1862900122292483165L;
    private boolean fast_game = false;

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    public void setFast_game(boolean b) {
        this.fast_game = b;
    }

    public boolean getFast_game() {
        return fast_game;
    }
}

package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

public class Hint extends Event {

    private static final long serialVersionUID = -2946048762706691702L;
    private String text = null;

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

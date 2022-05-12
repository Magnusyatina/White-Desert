package org.magnusario.whitedesert.engine.event;

public class Hint extends Event {

    private static final long serialVersionUID = -2946048762706691702L;
    private String text = null;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

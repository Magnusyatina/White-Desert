package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

public class CustomMusic extends Event {

    private static final long serialVersionUID = 8781614091959509519L;
    private String music_name = null;

    public void set_music_name(String music_name) {
        this.music_name = music_name;
    }

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    public String get_music_name() {
        return music_name;
    }
}

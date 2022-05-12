package org.magnusario.whitedesert.engine.event;

public class CustomMusic extends Event {

    private static final long serialVersionUID = 8781614091959509519L;
    private String music_name = null;

    public void set_music_name(String music_name) {
        this.music_name = music_name;
    }

    public String get_music_name() {
        return music_name;
    }
}

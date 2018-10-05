package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

public class CustomMusic extends Event {

    private String music_name = null;

    public void set_music_name(String music_name){
        this.music_name = music_name;
    }

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent( this );
    }

    public String get_music_name(){
        return music_name;
    }
}

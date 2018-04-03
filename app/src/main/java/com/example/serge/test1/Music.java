package com.example.serge.test1;

import android.media.MediaPlayer;

/**
 * Created by sergey37192 on 03.04.2018.
 */

public class Music {
    private static MediaPlayer mediaPlayer;

    public static void play(){
        if(mediaPlayer==null)
            createMediaPlayer();
        mediaPlayer.start();

    }

    public static void stop(){
        if(mediaPlayer!=null)
            mediaPlayer.pause();
    }

    private static void createMediaPlayer(){
        mediaPlayer = MediaPlayer.create( Shared.context, R.raw.soundtrack );
        mediaPlayer.setVolume( 0.5f, 0.5f );
        mediaPlayer.setLooping( true );
    }
}

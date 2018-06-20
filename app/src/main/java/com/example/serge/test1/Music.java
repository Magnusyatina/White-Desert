package com.example.serge.test1;

import android.media.MediaPlayer;

/**
 * Created by sergey37192 on 03.04.2018.
 */

public class Music {
    private static MediaPlayer mediaPlayer;

    public static void play(){
        if(mediaPlayer!=null)
            mediaPlayer.start();

    }

    public static void stop(){
        if(mediaPlayer!=null)
            mediaPlayer.pause();
    }

    public static void createMediaPlayer(){
        String music_name = Shared.properties.getProperty( "music_title" );
        if(music_name == null)
            return;

        int resId = Shared.context.getResources().getIdentifier( music_name, "raw", Shared.context.getPackageName() );
        if(resId == 0)
            return;
        createMediaPlayer( resId );

    }

    public static void createMediaPlayer(int resId){
        if(mediaPlayer != null&&mediaPlayer.isPlaying())
            mediaPlayer.release();
        mediaPlayer = MediaPlayer.create( Shared.context, resId );
        mediaPlayer.setVolume( 0.5f, 0.5f );
        mediaPlayer.setLooping( true );

    }
}

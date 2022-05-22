package org.magnusario.whitedesert.engine.listener;

import android.content.Context;

import org.magnusario.whitedesert.Music;
import org.magnusario.whitedesert.Shared;
import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.event.CustomMusic;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomMusicListener extends AbstractEventListener<CustomMusic> {

    @Inject
    public CustomMusicListener() {
    }

    @Override
    public void handle(CustomMusic music) {
        String musicName = music.get_music_name();
        Context context = getContext();
        int resId = context.getResources().getIdentifier(musicName, "raw", context.getPackageName());
        if (resId != 0) {
            Music.createMediaPlayer(resId);
            Shared.properties.setProperty("music_title", musicName);
            if (Shared.properties.getProperty("music").equals("on"))
                Music.play();
        }
        WWProgress.getProgressList().remove(music);
    }

    @Override
    public Class<CustomMusic> getHandledObjectClass() {
        return CustomMusic.class;
    }
}

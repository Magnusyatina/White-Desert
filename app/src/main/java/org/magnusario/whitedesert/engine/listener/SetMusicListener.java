package org.magnusario.whitedesert.engine.listener;

import org.magnusario.whitedesert.Music;
import org.magnusario.whitedesert.engine.event.SetMusic;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SetMusicListener extends AbstractEventListener<SetMusic> {

    @Inject
    public SetMusicListener() {
    }

    @Override
    public void handle(SetMusic setMusic) {
        if (setMusic.isEnable())
            Music.play();
        else Music.stop();
    }

    @Override
    public Class<SetMusic> getHandledObjectClass() {
        return SetMusic.class;
    }
}

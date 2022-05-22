package org.magnusario.whitedesert.engine.listener;

import org.magnusario.whitedesert.Music;
import org.magnusario.whitedesert.engine.event.SetMusic;

public class SetMusicListener extends AbstractEventListener<SetMusic> {

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

package org.magnusario.whitedesert.engine.listener;

import org.magnusario.whitedesert.EventTimer;
import org.magnusario.whitedesert.Shared;
import org.magnusario.whitedesert.engine.event.SetGameMode;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SetGameModeListener extends AbstractEventListener<SetGameMode> {

    @Inject
    public SetGameModeListener() {
    }

    @Override
    public void handle(SetGameMode gameMode) {
        boolean isgame_mode = gameMode.getFast_game();
        if (isgame_mode) {
            Shared.properties.setProperty("fast_game", "on");
        } else {
            Shared.properties.setProperty("fast_game", "off");
        }
        EventTimer.set_mode(isgame_mode);
    }

    @Override
    public Class<SetGameMode> getHandledObjectClass() {
        return SetGameMode.class;
    }
}

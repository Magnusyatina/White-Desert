package org.magnusario.whitedesert.engine.listener;

import android.support.v7.widget.SwitchCompat;

import org.magnusario.whitedesert.EventTimer;
import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.Shared;
import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.EventPool;
import org.magnusario.whitedesert.engine.event.Event;
import org.magnusario.whitedesert.engine.event.StartGame;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;

@Singleton
public class StartGameListener extends AbstractEventListener<StartGame> {

    @Inject
    public Lazy<EventPool> eventPool;

    @Inject
    public StartGameListener() {
    }

    @Override
    public void handle(StartGame event) {
        configureApplication();
        ArrayList<Event> arrayList;
        if ((arrayList = WWProgress.getProgressList()).size() > 0)
            gameContinue(arrayList);
        else moveStage(null);
    }

    private void configureApplication() {
        String fastGameProperty = Shared.properties.getProperty("fast_game");
        boolean isFastGameMode = "on".equals(fastGameProperty);
        EventTimer.set_mode(isFastGameMode);
        SwitchCompat switchCompat = (SwitchCompat) getViewManager().findViewById(R.id.game_condition_switch);
        if (switchCompat != null)
            switchCompat.setChecked(isFastGameMode);
    }

    @Override
    public Class<StartGame> getHandledObjectClass() {
        return StartGame.class;
    }

    public void gameContinue(ArrayList<Event> events) {
        for (Event e : events) {
            long time = e.getTimer();
            if (time > 0)
                getEventPool().submit(e, time);
            else getEventPool().submit(e);
        }
    }

    public void moveStage(String stageId) {
        if (stageId == null)
            stageId = "start";
        try {
            ArrayList<Event> arrayList = WWProgress.addToProgress(stageId);
            if (arrayList == null)
                return;
            gameContinue(arrayList);
        } catch (NoSuchElementException ex) {
            // Shared.activity.finish();
        }
    }
}

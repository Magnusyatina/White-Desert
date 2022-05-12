package org.magnusario.whitedesert.engine.listener;

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
        ArrayList<Event> arrayList;
        if ((arrayList = WWProgress.getProgressList()).size() > 0)
            gameContinue(arrayList);
        else selectStage(null);
    }

    @Override
    public Class<StartGame> getHandledObjectClass() {
        return StartGame.class;
    }

    public void gameContinue(ArrayList<Event> events) {
        for (Event e : events) {
            long time = e.getTimer();
            if (time > 0)
                getEventPool().notify(e, time);
            else getEventPool().notify(e);
        }
    }

    public void selectStage(String stageId) {
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

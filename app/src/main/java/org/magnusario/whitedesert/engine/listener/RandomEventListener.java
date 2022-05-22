package org.magnusario.whitedesert.engine.listener;

import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.event.Event;
import org.magnusario.whitedesert.engine.event.RandomEvent;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class RandomEventListener extends AbstractEventListener<RandomEvent> {

    @Override
    public void handle(RandomEvent randomEvent) {
        String stageId = randomEvent.getStage();
        moveStage(stageId);
        WWProgress.getProgressList().remove(randomEvent);
    }

    @Override
    public Class<RandomEvent> getHandledObjectClass() {
        return RandomEvent.class;
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

    public void gameContinue(ArrayList<Event> events) {
        for (Event e : events) {
            long time = e.getTimer();
            if (time > 0)
                getEventPool().submit(e, time);
            else getEventPool().submit(e);
        }
    }
}

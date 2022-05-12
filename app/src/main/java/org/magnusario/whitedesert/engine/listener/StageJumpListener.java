package org.magnusario.whitedesert.engine.listener;

import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.event.Event;
import org.magnusario.whitedesert.engine.event.StageJump;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.inject.Inject;

public class StageJumpListener extends AbstractEventListener<StageJump> {

    @Inject
    public StageJumpListener() {
    }

    @Override
    public void handle(StageJump stageJump) {
        String stageId = stageJump.getStage();
        if (stageId == null)
            return;
        selectStage(stageId);
        WWProgress.getProgressList().remove(stageJump);
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

    @Override
    public Class<StageJump> getHandledObjectClass() {
        return StageJump.class;
    }
}

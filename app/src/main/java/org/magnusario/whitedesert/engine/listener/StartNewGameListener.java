package org.magnusario.whitedesert.engine.listener;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.event.Event;
import org.magnusario.whitedesert.engine.event.StartNewGame;
import org.magnusario.whitedesert.view.ViewManager;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StartNewGameListener extends AbstractEventListener<StartNewGame> {

    @Inject
    public StartNewGameListener() {
    }

    @Override
    public void handle(StartNewGame event) {
        ViewGroup mainLayout = (ViewGroup) viewManager.findViewById(R.id.textArea);
        WWProgress.clearProgress();
        mainLayout.removeAllViews();
        clearSubElements();
        moveStage(null);
    }

    @Override
    public Class<StartNewGame> getHandledObjectClass() {
        return StartNewGame.class;
    }

    public void clearSubElements() {
        ViewManager viewManager = getViewManager();
        ((FrameLayout) getActivity().findViewById(R.id.FragmentContainer)).removeAllViews();
        LinearLayout mainLayoutFrame = (LinearLayout) viewManager.findViewById(R.id.MainLayout);
        View v = mainLayoutFrame.findViewById(R.id.SubLayout);
        if (v != null)
            mainLayoutFrame.removeView(v);
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

package org.magnusario.whitedesert.engine;

import android.app.Activity;
import android.content.Intent;
import android.widget.ScrollView;

import org.magnusario.whitedesert.GameProcessService;
import org.magnusario.whitedesert.Music;
import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.Scenario;
import org.magnusario.whitedesert.Shared;
import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.event.Event;
import org.magnusario.whitedesert.view.ViewManager;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EngineImpl extends EngineAdapter {

    @Inject
    public EventPool eventPool;

    @Inject
    public Activity activity;

    @Inject
    public ViewManager viewManager;

    @Inject
    public EngineImpl() {
    }

    public void onCreate() {
        super.onCreate();
        try {
            Scenario.loadSceanrio(Shared.context);
            WWProgress.loadProgress(Shared.context);
            Music.createMediaPlayer(Shared.properties.getProperty("music_title"));
            Music.play();
            onResume();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        viewManager.clear();
        WWProgress.saveProgress(Shared.context);
        Event lastEvent = WWProgress.getLastEvent();
        if (lastEvent != null) {
            Shared.context.startService(new Intent(Shared.context, GameProcessService.class).putExtra("SCHEDULE_TIME", lastEvent.getScheduledtime()));
        }
    }

    @Override
    public void onResume() {
        Shared.context.stopService(new Intent(Shared.context, GameProcessService.class));
        scrollDown();
    }

    public void scrollDown() {
        ScrollView view = (ScrollView) viewManager.findViewById(R.id.mainScrollView);
        view.postDelayed(() -> view.fullScroll(ScrollView.FOCUS_DOWN), 250);
    }

}

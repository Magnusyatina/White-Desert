package org.magnusario.whitedesert.engine;

import android.app.Activity;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.magnusario.whitedesert.GameProcessService;
import org.magnusario.whitedesert.Music;
import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.Scenario;
import org.magnusario.whitedesert.Shared;
import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.event.Event;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EngineImpl extends EngineAdapter {

    FrameLayout mainFrame;
    LinearLayout mainLayoutFrame;

    LinearLayout mainLayout;
    LinearLayout questionView;
    ScrollView mainScrollView;

    @Inject
    public EventPool eventPool;

    @Inject
    public Activity activity;

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
            mainFrame = (FrameLayout) activity.findViewById(R.id.MainFrame);
            mainLayoutFrame = (LinearLayout) activity.findViewById(R.id.MainLayout);
            mainLayout = (LinearLayout) activity.findViewById(R.id.textArea);
            mainScrollView = (ScrollView) activity.findViewById(R.id.mainScrollView);
            onResume();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        WWProgress.saveProgress(Shared.context);
        Event lastEvent = WWProgress.getLastEvent();
        if (lastEvent != null)
            Shared.context.startService(new Intent(Shared.context, GameProcessService.class).putExtra("SCHEDULE_TIME", lastEvent.getScheduledtime()));
    }

    @Override
    public void onResume() {
        Shared.context.stopService(new Intent(Shared.context, GameProcessService.class));
        scrollDown();
    }

    public void scrollDown() {

        mainLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 250);
    }

}

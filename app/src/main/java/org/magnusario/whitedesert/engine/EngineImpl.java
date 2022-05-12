package org.magnusario.whitedesert.engine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.magnusario.whitedesert.AnimationListenerAdapter;
import org.magnusario.whitedesert.CustomTimer;
import org.magnusario.whitedesert.GameProcessService;
import org.magnusario.whitedesert.Music;
import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.Scenario;
import org.magnusario.whitedesert.Shared;
import org.magnusario.whitedesert.Triggers;
import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.event.CustomMusic;
import org.magnusario.whitedesert.engine.event.Die;
import org.magnusario.whitedesert.engine.event.Event;
import org.magnusario.whitedesert.engine.event.Hint;
import org.magnusario.whitedesert.engine.event.ImportantMessage;
import org.magnusario.whitedesert.engine.event.PlayerAnswer;
import org.magnusario.whitedesert.engine.event.Question;
import org.magnusario.whitedesert.engine.event.Questions;
import org.magnusario.whitedesert.engine.event.RandomEvent;
import org.magnusario.whitedesert.engine.event.SetGameMode;
import org.magnusario.whitedesert.engine.event.SetMusic;
import org.magnusario.whitedesert.engine.event.StageJump;
import org.magnusario.whitedesert.engine.event.TextMessage;
import org.magnusario.whitedesert.engine.event.Waiting;
import org.magnusario.whitedesert.view.CustomButton;
import org.magnusario.whitedesert.view.CustomHintDiaolog;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sergey37192 on 04.04.2018.
 */

@Singleton
public class EngineImpl extends EventAdapter {

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

    public void gameContinue(ArrayList<Event> events) {
        for (Event e : events) {
            long time = e.getTimer();
            if (time > 0)
                Shared.eventPool.notify(e, time);
            else Shared.eventPool.notify(e);
        }
    }

    @Override
    public void onEvent(CustomMusic music) {
        String str_music = music.get_music_name();
        int resId = Shared.context.getResources().getIdentifier(str_music, "raw", Shared.context.getPackageName());
        if (resId != 0) {
            Music.createMediaPlayer(resId);
            Shared.properties.setProperty("music_title", str_music);
            if (Shared.properties.getProperty("music").equals("on"))
                Music.play();
        }
        WWProgress.getProgressList().remove(music);
    }

    @Override
    public void onEvent(RandomEvent randomEvent) {
        String stageId = randomEvent.getStage();
        selectStage(stageId);
        WWProgress.getProgressList().remove(randomEvent);
    }

    public void onEvent(Die die) {
        LayoutInflater layoutInflater = Shared.activity.getLayoutInflater();
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.die_icon, mainLayout, false);
        mainLayout.addView(imageView);
        die.setAdded(true);
        scrollDown();
    }

    @Override
    public void onEvent(SetGameMode gameMode) {
        boolean isgame_mode = gameMode.getFast_game();
        if (isgame_mode) {
            Shared.properties.setProperty("fast_game", "on");
        } else {
            Shared.properties.setProperty("fast_game", "off");
        }
        CustomTimer.set_mode(isgame_mode);
    }

    @Override
    public void onEvent(SetMusic setMusic) {
        if (setMusic.isEnable())
            Music.play();
        else Music.stop();
    }

    @Override
    public void onEvent(ImportantMessage importantMessage) {

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

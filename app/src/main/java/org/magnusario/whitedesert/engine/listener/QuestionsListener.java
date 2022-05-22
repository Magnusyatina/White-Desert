package org.magnusario.whitedesert.engine.listener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.magnusario.whitedesert.AnimationListenerAdapter;
import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.Scenario;
import org.magnusario.whitedesert.Shared;
import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.ApplicationConstants;
import org.magnusario.whitedesert.engine.event.Event;
import org.magnusario.whitedesert.engine.event.PlayerAnswer;
import org.magnusario.whitedesert.engine.event.Question;
import org.magnusario.whitedesert.engine.event.Questions;
import org.magnusario.whitedesert.view.CustomButton;
import org.magnusario.whitedesert.view.ViewManager;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class QuestionsListener extends AbstractEventListener<Questions> {

    @Inject
    public ViewManager viewManager;

    @Inject
    public QuestionsListener() {
    }

    @Override
    public void handle(final Questions event) {
        final ViewGroup mainLayoutFrame = (ViewGroup) viewManager.findViewById(R.id.MainLayout);
        final FrameLayout subLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.question_layout, mainLayoutFrame, false);
        LinearLayout questionView = subLayout.findViewById(R.id.questionsLayout);

        ArrayList<Question> questionArray = event.getList();
        //animation open
        mainLayoutFrame.addView(subLayout);
        Animation anim = AnimationUtils.loadAnimation(Shared.context, R.anim.animscalemaximize);
        anim.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationStart(Animation animation) {
                subLayout.setVisibility(View.VISIBLE);
                scrollDown();
            }
        });
        subLayout.startAnimation(anim);

        for (Question q : questionArray) {
            LayoutInflater layoutInflater = Shared.activity.getLayoutInflater();
            final CustomButton customButton = (CustomButton) layoutInflater.inflate(R.layout.button_view, questionView, false);
            customButton.setGoTo(q.getTarget());
            customButton.setText(q.getText());
            customButton.setOnClickListener(v -> {
                onAnswerChoose(event, mainLayoutFrame, subLayout, customButton);
            });
            if (customButton.getGoTo() == null || !Scenario.scenarioList.containsKey(customButton.getGoTo()))
                customButton.setEnabled(false);
            questionView.addView(customButton);
        }
    }

    private void onAnswerChoose(Questions event, ViewGroup mainLayoutFrame, FrameLayout subLayout, CustomButton customButton) {
        event.setHandled(true);
        PlayerAnswer playerAnswer = new PlayerAnswer();
        playerAnswer.setStage(event.getStage());
        playerAnswer.setText(customButton.getText().toString());
        ((LinearLayout) viewManager.findViewById(R.id.questionsLayout)).removeAllViews();
        WWProgress.getProgressList().remove(event);
        WWProgress.getProgressList().add(playerAnswer);
        getEventPool().submit(playerAnswer);
        Animation anim1 = AnimationUtils.loadAnimation(Shared.context, R.anim.animscaleminimize);
        anim1.setFillAfter(false);
        anim1.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                mainLayoutFrame.removeView(subLayout);
            }
        });
        subLayout.startAnimation(anim1);
        scrollDown();
        moveStage(customButton.getGoTo());
    }

    @Override
    public Class<Questions> getHandledObjectClass() {
        return Questions.class;
    }

    public void scrollDown() {
        ViewManager viewManager = getViewManager();
        viewManager.findViewById(R.id.textArea).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((ScrollView) viewManager.findViewById(R.id.mainScrollView)).fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, ApplicationConstants.SCROLL_DELAY_MILLIS);
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

package org.magnusario.whitedesert.engine.listener;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.Triggers;
import org.magnusario.whitedesert.engine.ApplicationConstants;
import org.magnusario.whitedesert.engine.event.TextMessage;
import org.magnusario.whitedesert.view.CustomPersonAnswer;
import org.magnusario.whitedesert.view.ViewManager;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TextMessageListener extends AbstractEventListener<TextMessage> {

    @Inject
    public Activity activity;

    @Inject
    public TextMessageListener() {
    }

    @Override
    public void handle(TextMessage event) {
        ViewManager viewManager = getViewManager();
        if (!Triggers.isEmpty()) {
            ArrayList<Runnable> runnables = Triggers.getTriggers(event);
            if (runnables != null) {
                runTriggers(runnables);
                Triggers.removeAllTriggers(event);
            }
        }

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View mainLayout = viewManager.findViewById(R.id.textArea);
        final CustomPersonAnswer customPersonAnswer = (CustomPersonAnswer) layoutInflater.inflate(R.layout.custompersonanswer, (ViewGroup) mainLayout, false);
        customPersonAnswer.setText(event.getText());
        ((ViewGroup) mainLayout).addView(customPersonAnswer);
        event.setHandled(true);
        scrollDown();
    }

    public void runTriggers(ArrayList<Runnable> list) {

        for (Runnable runnable : list) {
            eventPool.get().submit(runnable);
        }
    }

    @Override
    public Class<TextMessage> getHandledObjectClass() {
        return TextMessage.class;
    }

    public void scrollDown() {
        ViewManager viewManager = getViewManager();
        this.viewManager.findViewById(R.id.textArea).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((ScrollView) viewManager.findViewById(R.id.mainScrollView)).fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, ApplicationConstants.SCROLL_DELAY_MILLIS);
    }
}

package org.magnusario.whitedesert.engine.listener;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.Triggers;
import org.magnusario.whitedesert.engine.ApplicationConstants;
import org.magnusario.whitedesert.engine.event.TextMessage;
import org.magnusario.whitedesert.engine.event.Waiting;
import org.magnusario.whitedesert.view.ViewManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WaitingListener extends AbstractEventListener<Waiting> {

    private static final int[] STATE_SET_NON_WAITING = {-R.attr.state_waiting, R.attr.state_non_waiting};

    @Inject
    public WaitingListener() {
    }

    @Override
    public void handle(Waiting waiting) {
        if (waiting.isHandled())
            return;
        ViewManager viewManager = getViewManager();
        ViewGroup mainLayout = (ViewGroup) viewManager.findViewById(R.id.MainLayout);
        final ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.waiting_view, mainLayout, false);
        mainLayout.addView(imageView);
        final Drawable dr = imageView.getDrawable();
        imageView.setImageState(STATE_SET_NON_WAITING, true);
        if (dr instanceof Animatable) {
            ((Animatable) dr).start();
            Triggers.addTrigger(TextMessage.class.getName(), () -> {
                waiting.setHandled(true);
                mainLayout.removeView(imageView);
                ((Animatable) dr).stop();
                Triggers.removeAllTriggers(waiting);
            });
        }
        scrollDown();
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

    @Override
    public Class<Waiting> getHandledObjectClass() {
        return Waiting.class;
    }
}

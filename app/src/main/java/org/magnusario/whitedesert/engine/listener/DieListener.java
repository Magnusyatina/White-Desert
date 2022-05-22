package org.magnusario.whitedesert.engine.listener;

import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.engine.ApplicationConstants;
import org.magnusario.whitedesert.engine.event.Die;
import org.magnusario.whitedesert.view.ViewManager;

public class DieListener extends AbstractEventListener<Die> {

    @Override
    public void handle(Die die) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        LinearLayout mainLayout = (LinearLayout) getViewManager().findViewById(R.id.textArea);
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.die_icon, mainLayout, false);
        mainLayout.addView(imageView);
        die.setHandled(true);
        scrollDown();
    }

    @Override
    public Class<Die> getHandledObjectClass() {
        return Die.class;
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

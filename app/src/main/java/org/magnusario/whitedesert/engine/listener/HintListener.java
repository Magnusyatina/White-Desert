package org.magnusario.whitedesert.engine.listener;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.event.Hint;
import org.magnusario.whitedesert.view.CustomHintDiaolog;
import org.magnusario.whitedesert.view.ViewManager;

import javax.inject.Inject;

public class HintListener extends AbstractEventListener<Hint> {

    public static final String DIALOG_TAG = "HintDialog";

    @Inject
    public HintListener() {
    }

    @Override
    public void handle(Hint hint) {
        if (hint.isHandled())
            return;
        Activity activity = getActivity();
        ViewManager viewManager = getViewManager();
        FrameLayout mainFrame = (FrameLayout) viewManager.findViewById(R.id.MainFrame);
        final ImageView hint_icon = (ImageView) activity.getLayoutInflater().inflate(R.layout.hint_icon, mainFrame, false);
        mainFrame.addView(hint_icon);
        Drawable dr = hint_icon.getDrawable();

        if (dr instanceof Animatable)
            ((Animatable) dr).start();

        hint_icon.setOnClickListener(v -> {
            hint.setHandled(true);
            CustomHintDiaolog dialog = new CustomHintDiaolog();
            dialog.setText(hint.getText());
            dialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), DIALOG_TAG);
            WWProgress.getProgressList().remove(hint);
            mainFrame.removeView(hint_icon);
        });
    }

    @Override
    public Class<Hint> getHandledObjectClass() {
        return Hint.class;
    }
}

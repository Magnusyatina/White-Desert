package org.magnusario.whitedesert.engine.listener;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.WWProgress;
import org.magnusario.whitedesert.engine.EventPool;
import org.magnusario.whitedesert.engine.event.Event;
import org.magnusario.whitedesert.engine.event.PlayerAnswer;
import org.magnusario.whitedesert.engine.event.Questions;
import org.magnusario.whitedesert.view.CustomButtonPlayerAnswer;

import javax.inject.Inject;

public class PlayerAnswerListener extends AbstractEventListener<PlayerAnswer> {

    @Inject
    public PlayerAnswerListener() {
    }

    @Override
    public void handle(PlayerAnswer playerAnswer) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        LinearLayout mainLayout = (LinearLayout) getViewManager().findViewById(R.id.textArea);
        final CustomButtonPlayerAnswer customButtonAnswer = (CustomButtonPlayerAnswer) layoutInflater.inflate(R.layout.custombuttonanswer, mainLayout, false);
        customButtonAnswer.setText(playerAnswer.getText());
        customButtonAnswer.setOnLongClickListener(v -> {
            Toast.makeText(getContext(), "Сработало событие", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Вы хотите вернуться на этот этап?")
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, onPositiveAnswer(playerAnswer, mainLayout, customButtonAnswer))
                    .setNegativeButton(R.string.no, onNegativeAnswer());
            builder.show();
            return false;
        });
        mainLayout.addView(customButtonAnswer);
        playerAnswer.setHandled(true);
    }

    @NonNull
    private DialogInterface.OnClickListener onNegativeAnswer() {
        return (dialog, which) -> dialog.cancel();
    }

    @NonNull
    private DialogInterface.OnClickListener onPositiveAnswer(PlayerAnswer playerAnswer, LinearLayout mainLayout, CustomButtonPlayerAnswer customButtonAnswer) {
        return (dialog, which) -> {
            EventPool eventPool = getEventPool();
            eventPool.stopAll();
            WWProgress.backInTime(playerAnswer);
            int start = mainLayout.indexOfChild(customButtonAnswer);
            int count = mainLayout.getChildCount() - start;
            mainLayout.removeViews(start, count);
            clearSubElements();
            Event qe = WWProgress.getEventById(playerAnswer.getStage(), Questions.class);
            if (qe != null) {
                qe.setHandled(false);
                eventPool.submit(qe);
            }
        };
    }

    public void clearSubElements() {
        ((FrameLayout) getActivity().findViewById(R.id.FragmentContainer)).removeAllViews();
        LinearLayout mainLayoutFrame = (LinearLayout) viewManager.findViewById(R.id.MainLayout);
        View v = mainLayoutFrame.findViewById(R.id.SubLayout);
        if (v != null)
            mainLayoutFrame.removeView(v);
    }

    @Override
    public Class<PlayerAnswer> getHandledObjectClass() {
        return PlayerAnswer.class;
    }
}

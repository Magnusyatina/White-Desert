package org.magnusario.whitedesert;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.magnusario.whitedesert.event.CustomMusic;
import org.magnusario.whitedesert.event.Die;
import org.magnusario.whitedesert.event.Event;
import org.magnusario.whitedesert.event.Hint;
import org.magnusario.whitedesert.event.ImportantMessage;
import org.magnusario.whitedesert.event.PlayerAnswer;
import org.magnusario.whitedesert.event.Question;
import org.magnusario.whitedesert.event.Questions;
import org.magnusario.whitedesert.event.RandomEvent;
import org.magnusario.whitedesert.event.SetGameMode;
import org.magnusario.whitedesert.event.SetMusic;
import org.magnusario.whitedesert.event.StageJump;
import org.magnusario.whitedesert.event.StartGame;
import org.magnusario.whitedesert.event.StartNewGame;
import org.magnusario.whitedesert.event.TacticalEvent;
import org.magnusario.whitedesert.event.TextMessage;
import org.magnusario.whitedesert.event.Waiting;
import org.magnusario.whitedesert.view.CustomButton;
import org.magnusario.whitedesert.view.CustomButtonPlayerAnswer;
import org.magnusario.whitedesert.view.CustomHintDiaolog;
import org.magnusario.whitedesert.view.CustomPersonAnswer;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by sergey37192 on 04.04.2018.
 */

public class Engine extends EventObserverAdapter {

    private long scheduletime = 0;
    FrameLayout mainFrame;
    LinearLayout mainLayoutFrame;

    LinearLayout mainLayout;
    LinearLayout questionView;
    ScrollView mainScrollView;


    public void onCreate() {
        super.onCreate();
        try {
            Scenario.loadSceanrio(Shared.context);
            WWProgress.loadProgress(Shared.context);
            Music.createMediaPlayer(Shared.properties.getProperty("music_title"));
            Music.play();
            mainFrame = (FrameLayout) Shared.activity.findViewById(R.id.MainFrame);
            mainLayoutFrame = (LinearLayout) Shared.activity.findViewById(R.id.MainLayout);
            mainLayout = (LinearLayout) Shared.activity.findViewById(R.id.textArea);
            mainScrollView = (ScrollView) Shared.activity.findViewById(R.id.mainScrollView);
            onResume();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }


    public void getCurrentEpisode(String stageId) {
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
            this.scheduletime = e.getScheduledtime();
        }
    }


    @Override
    public void onEvent(final Hint hint) {

        final ImageView hint_icon = (ImageView) Shared.activity.getLayoutInflater().inflate(R.layout.hint_icon, mainFrame, false);
        mainFrame.addView(hint_icon);
        Drawable dr = hint_icon.getDrawable();


        if (dr instanceof Animatable) {
            ((Animatable) dr).start();
        }

        hint_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomHintDiaolog dialog = new CustomHintDiaolog();
                dialog.setText(hint.getText());
                dialog.show(((AppCompatActivity) Shared.activity).getSupportFragmentManager(), "HintDialog");
                WWProgress.getProgressList().remove(hint);
                mainFrame.removeView(hint_icon);
            }
        });


    }

    @Override
    public void onEvent(TacticalEvent tacticalEvent) {
        super.onEvent(tacticalEvent);
        Toast.makeText(Shared.context, "Сработало тактическое событие", Toast.LENGTH_SHORT).show();

        //TacticalDialogFragment tdf = new TacticalDialogFragment();
        //tdf.setMainNode(tacticalEvent);
        //tdf.show(((AppCompatActivity)Shared.activity).getSupportFragmentManager(), "TacticalDialogFragment");
       /* TacticalFragment tacticalFragment = new TacticalFragment();
        tacticalFragment.setNode(tacticalEvent);
        FragmentManager fm = ((AppCompatActivity) Shared.activity).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.FragmentContainer, tacticalFragment);
        ft.commit();*/
       /* LayoutInflater inflater = Shared.activity.getLayoutInflater();
        ImageView imageView = (ImageView) inflater.inflate( R.layout.customimageview, mainLayout, false );
        mainLayout.addView( imageView );
        Drawable drawable = imageView.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }*/

    }

    @Override
    public void onEvent(StartGame startGame) {
        ArrayList<Event> arrayList = null;
        if ((arrayList = WWProgress.getProgressList()).size() > 0)
            gameContinue(arrayList);
        else getCurrentEpisode(null);
    }

    @Override
    public void onEvent(StartNewGame startNewGame) {
        WWProgress.dump_of_progress();
        mainLayout.removeAllViews();
        clearSubElements();
        getCurrentEpisode(null);
    }

    public void clearSubElements() {
        ((FrameLayout) Shared.activity.findViewById(R.id.FragmentContainer)).removeAllViews();

        View v = mainLayoutFrame.findViewById(R.id.SubLayout);
        if (v != null)
            mainLayoutFrame.removeView(v);
    }

    public void runTrigger(Runnable runnable) {
        Shared.eventPool.notify(runnable);
    }


    public void runTriggers(ArrayList<Runnable> list) {

        for (Runnable runnable : list) {
            runTrigger(runnable);
        }
    }

    //Вывод сообщения от персонажа на экран
    public void onEvent(TextMessage textMessage) {
        if (!Triggers.isEmpty()) {
            ArrayList<Runnable> runnables = Triggers.getTriggers(textMessage);
            if (runnables != null) {
                runTriggers(runnables);
                Triggers.removeAllTriggers(textMessage);
            }
        }

        LayoutInflater layoutInflater = Shared.activity.getLayoutInflater();
        final CustomPersonAnswer customPersonAnswer = (CustomPersonAnswer) layoutInflater.inflate(R.layout.custompersonanswer, mainLayout, false);
        customPersonAnswer.setText(textMessage.getText());
        mainLayout.addView(customPersonAnswer);
        textMessage.setAdded(true);
        scrollDown();
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
        getCurrentEpisode(stageId);
        WWProgress.getProgressList().remove(randomEvent);
    }

    public void onEvent(StageJump stageJump) {
        String stageId = stageJump.getStage();
        if (stageId == null)
            return;
        getCurrentEpisode(stageId);
        WWProgress.getProgressList().remove(stageJump);
    }

    //Вывод ответа пользователя на экран
    public void onEvent(final PlayerAnswer playerAnswer) {
        LayoutInflater layoutInflater = Shared.activity.getLayoutInflater();
        final CustomButtonPlayerAnswer customButtonAnswer = (CustomButtonPlayerAnswer) layoutInflater.inflate(R.layout.custombuttonanswer, mainLayout, false);
        customButtonAnswer.setText(playerAnswer.getText());
        customButtonAnswer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(Shared.context, "Сработало событие", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Shared.context);
                builder.setMessage("Вы хотите вернуться на этот этап?")
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Shared.eventPool.stopAll();
                                WWProgress.backInTime(playerAnswer);
                                int start = mainLayout.indexOfChild(customButtonAnswer);
                                int count = mainLayout.getChildCount() - start;
                                mainLayout.removeViews(start, count);
                                clearSubElements();
                                Event qe = WWProgress.getEventById(playerAnswer.getStage(), Questions.class);
                                if (qe != null) {
                                    qe.setAdded(false);
                                    Shared.eventPool.notify(qe);
                                }
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
                return false;
            }
        });
        mainLayout.addView(customButtonAnswer);
        playerAnswer.setAdded(true);
    }

    //Вывод вопросов на экран
    public void onEvent(final Questions questions) {
        final FrameLayout subLayout = (FrameLayout) LayoutInflater.from(Shared.context).inflate(R.layout.question_layout, mainLayoutFrame, false);
        questionView = (LinearLayout) subLayout.findViewById(R.id.questionsLayout);

        ArrayList<Question> questionArray = questions.getList();
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
            customButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questions.setAdded(true);
                    PlayerAnswer playerAnswer = new PlayerAnswer();
                    playerAnswer.setStage(questions.getStage());
                    playerAnswer.setText(customButton.getText().toString());

                    ((LinearLayout) Shared.activity.findViewById(R.id.questionsLayout)).removeAllViews();


                    WWProgress.getProgressList().remove(questions);
                    WWProgress.getProgressList().add(playerAnswer);
                    Shared.eventPool.notify(playerAnswer);
                    //animation close
                    Animation anim = AnimationUtils.loadAnimation(Shared.context, R.anim.animscaleminimize);
                    anim.setFillAfter(false);
                    anim.setAnimationListener(new AnimationListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mainLayoutFrame.removeView(subLayout);
                        }

                    });
                    subLayout.startAnimation(anim);

                    //
                    scrollDown();
                    getCurrentEpisode(customButton.getGoTo());
                }
            });
            if (customButton.getGoTo() == null || !Scenario.scenarioList.containsKey(customButton.getGoTo()))
                customButton.setEnabled(false);
            questionView.addView(customButton);
        }
    }


    private static final int[] STATE_SET_WAITING = {R.attr.state_waiting, -R.attr.state_non_waiting};
    private static final int[] STATE_SET_NON_WAITING = {-R.attr.state_waiting, R.attr.state_non_waiting};

    public void onEvent(final Waiting waiting) {
        final ImageView view = (ImageView) LayoutInflater.from(Shared.context).inflate(R.layout.waiting_view, mainLayout, false);
        mainLayout.addView(view);
        final Drawable dr = view.getDrawable();

        view.setImageState(STATE_SET_NON_WAITING, true);

        if (dr instanceof Animatable) {
            ((Animatable) dr).start();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ((Animatable) dr).stop();
                    Triggers.removeAllTriggers(waiting);
                }
            };
            Triggers.addTrigger(TextMessage.class.getName(), runnable);
        }


        scrollDown();

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
        Shared.context.startService(new Intent(Shared.context, GameProcessService.class).putExtra("SCHEDULE_TIME", scheduletime));
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

package com.example.serge.test1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serge.test1.CustomEvents.AddItem;
import com.example.serge.test1.CustomEvents.CustomMusic;
import com.example.serge.test1.CustomEvents.SetGameMode;
import com.example.serge.test1.CustomEvents.SetMusic;
import com.example.serge.test1.CustomEvents.StageJump;
import com.example.serge.test1.CustomEvents.TacticalEvent;
import com.example.serge.test1.CustomView.CustomButton;
import com.example.serge.test1.CustomEvents.CustomEvents;
import com.example.serge.test1.CustomEvents.Die;

import com.example.serge.test1.CustomEvents.ImportantMessage;
import com.example.serge.test1.CustomEvents.PlayerAnwser;
import com.example.serge.test1.CustomEvents.Question;
import com.example.serge.test1.CustomEvents.Questions;
import com.example.serge.test1.CustomEvents.RandomEvent;
import com.example.serge.test1.CustomEvents.RemoveItem;
import com.example.serge.test1.CustomEvents.StartGame;
import com.example.serge.test1.CustomEvents.StartNewGame;
import com.example.serge.test1.CustomEvents.TextMessage;
import com.example.serge.test1.CustomEvents.Waiting;
import com.example.serge.test1.CustomView.CustomButtonPlayerAnswer;
import com.example.serge.test1.CustomView.CustomPersonAnswer;
import com.example.serge.test1.CustomView.CustomWaitingView;
import com.example.serge.test1.CustomView.TacticalDialogFragment;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by sergey37192 on 04.04.2018.
 */

public class Engine extends EventObserverAdapter {

    private long scheduletime = 0;
    LinearLayout mainLayout;
    LinearLayout questionView;
    ScrollView mainScrollView;
    LinearLayout inventory;

    public void onCreate(){
        super.onCreate();
        try {
            Scenario.loadSceanrio( Shared.context );
            WWProgress.loadProgress( Shared.context );
            Music.createMediaPlayer(Shared.properties.getProperty( "music_title" ));
            Music.play();
            mainLayout = (LinearLayout) Shared.activity.findViewById( R.id.textArea );
            mainScrollView = (ScrollView) Shared.activity.findViewById( R.id.mainScrollView );
            questionView = (LinearLayout) Shared.activity.findViewById( R.id.questionsLayout );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }


    public void getCurrentEpisode(String stageId){
        if(stageId == null)
            stageId = "start";
        try{
        ArrayList<CustomEvents> arrayList = WWProgress.addToProgress( stageId );
        gameContinue( arrayList );}
        catch (NoSuchElementException ex){
            Shared.activity.finish();
        }
    }

    public void gameContinue(ArrayList<CustomEvents> events){
        for(CustomEvents e : events){
            long time = e.getTimer();
            if(time>0)
                Shared.eventPool.notify( e, time );
            else Shared.eventPool.notify(e);
            this.scheduletime = e.getScheduledtime();
        }
    }


    @Override
    public void onEvent(TacticalEvent tacticalEvent) {
        super.onEvent( tacticalEvent );
        Toast.makeText( Shared.context, "Сработало тактическое событие", Toast.LENGTH_SHORT ).show();
        final TacticalDialogFragment tacticalDialogFragment = new TacticalDialogFragment();
       // tacticalDialogFragment.show(Shared.activity.getFragmentManager(), "TacticalDialogFragment");
        new Handler( Looper.getMainLooper()).postDelayed( new Runnable() {
            @Override
            public void run() {
                //tacticalDialogFragment.changeText();
            }
        }, 5000 );
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
        ArrayList<CustomEvents> arrayList = null;
        if((arrayList =  WWProgress.getProgressList()).size()>0)
            gameContinue( arrayList );
        else getCurrentEpisode(null);
    }

    @Override
    public void onEvent(StartNewGame startNewGame) {
        WWProgress.dump_of_progress();
        mainLayout.removeAllViews();
        questionView.removeAllViews();
        getCurrentEpisode( null );
    }
    //Добавление предмета в инвентарь
    public void onEvent(final AddItem addItem){
        final int itemId;
        if((itemId = addItem.getItem())!=-1){
            WWProgress.setItem( itemId );
            WWProgress.getProgressList().remove( addItem );
        }
    }

    //Удаление предмета из инвентаря
    public void onEvent(RemoveItem removeItem){
        final int itemId;
        if((itemId = removeItem.getItem())!=-1){
            WWProgress.unsetItem( itemId );
            WWProgress.getProgressList().remove( removeItem );
        }
    }

    //Вывод сообщения от персонажа на экран
    public void onEvent(TextMessage textMessage){
        LayoutInflater layoutInflater = Shared.activity.getLayoutInflater();
        CustomPersonAnswer customPersonAnswer = (CustomPersonAnswer) layoutInflater.inflate( R.layout.custompersonanswer, mainLayout, false );
        customPersonAnswer.setText( textMessage.getText() );
        mainLayout.addView( customPersonAnswer );
        textMessage.setAdded( true );
        scrollDown();
    }

    @Override
    public void onEvent(CustomMusic music) {
        String str_music = music.get_music_name();
        int resId = Shared.context.getResources().getIdentifier( str_music, "raw", Shared.context.getPackageName() );
        if(resId != 0){
            Music.createMediaPlayer( resId );
            Shared.properties.setProperty( "music_title", str_music );
            if(Shared.properties.getProperty( "music" ).equals( "on" ))
                Music.play();
        }
        WWProgress.getProgressList().remove( music );
    }

    @Override
    public void onEvent(RandomEvent randomEvent) {
        String stageId = randomEvent.getTarget();
        getCurrentEpisode( stageId );
        WWProgress.getProgressList().remove( randomEvent );
    }

    public void onEvent(StageJump stageJump){
        String stageId = stageJump.getTarget();
        if(stageId == null)
            return;
        getCurrentEpisode( stageId );
        WWProgress.getProgressList().remove( stageJump );
    }
    //Вывод ответа пользователя на экран
    public void onEvent(final PlayerAnwser playerAnwser){
        LayoutInflater layoutInflater = Shared.activity.getLayoutInflater();
        final CustomButtonPlayerAnswer customButtonAnswer = (CustomButtonPlayerAnswer) layoutInflater.inflate( R.layout.custombuttonanswer, mainLayout, false);
        customButtonAnswer.setText( playerAnwser.getText() );
        customButtonAnswer.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(Shared.context, "Сработало событие", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Shared.context);
                builder.setMessage( "Вы хотите вернуться на этот этап?" )
                        .setCancelable( false )
                        .setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Shared.eventPool.stopAll();
                                WWProgress.backInTime( playerAnwser );
                                int start = mainLayout.indexOfChild( customButtonAnswer );
                                int count = mainLayout.getChildCount() - start;
                                mainLayout.removeViews( start, count );
                                questionView.removeAllViews();
                                CustomEvents qe = WWProgress.getLastEvent( Questions.class );
                                if(qe!=null){
                                    qe.setAdded( false );
                                    Shared.eventPool.notify( qe );
                                }
                            }
                        } )
                        .setNegativeButton( R.string.no, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        } );
                builder.show();
                return false;
            }
        } );
        mainLayout.addView( customButtonAnswer );
        playerAnwser.setAdded(true);
    }

    //Вывод вопросов на экран
    public void onEvent(final Questions questions){
        ArrayList<Question> questionArray = questions.getList();
        //animation open
        Animation anim = AnimationUtils.loadAnimation( Shared.context, R.anim.animscalemaximize );
        Shared.activity.findViewById( R.id.test).startAnimation( anim );
        anim.setAnimationListener( new AnimationListenerAdapter(){
            @Override
            public void onAnimationStart(Animation animation) {
                Shared.activity.findViewById( R.id.test).setVisibility( View.VISIBLE );
            }
        } );        //
        for(Question q : questionArray){
            int itemId = q.getNeedItem();
            if(itemId == -1 || WWProgress.checkItem( itemId )){
                LayoutInflater layoutInflater = Shared.activity.getLayoutInflater();
                final CustomButton customButton = (CustomButton) layoutInflater.inflate( R.layout.custombutton, questionView, false );
                customButton.setGoTo( q.getTarget() );
                customButton.setLayoutParams( Settings.questionViewParams );
                customButton.setText( q.getText() );
                customButton.setBackgroundResource( R.drawable.custombutton );
                customButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        questions.setAdded(true);
                        PlayerAnwser playerAnwser = new PlayerAnwser();
                        playerAnwser.setStage(questions.getTarget());
                        playerAnwser.setText(  customButton.getText().toString() );
                        WWProgress.getProgressList().add( playerAnwser );
                        Shared.eventPool.notify( playerAnwser );
                        questionView.removeAllViews();
                        //animation close
                        Animation anim = AnimationUtils.loadAnimation( Shared.context, R.anim.animscaleminimize );
                        Shared.activity.findViewById( R.id.test).startAnimation( anim );
                        anim.setAnimationListener( new AnimationListenerAdapter(){
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Shared.activity.findViewById( R.id.test).setVisibility( View.INVISIBLE );
                            }
                        } );
                        //
                        scrollDown();
                        getCurrentEpisode(customButton.getGoTo());
                    }
                } );
                if(customButton.getGoTo()==null||!Scenario.scenarioList.containsKey( customButton.getGoTo() ))
                    customButton.setEnabled( false );
                questionView.addView(customButton);
            }
        }
    }

    public void onEvent(Waiting waiting){
        final Waiting wait = waiting;
        LayoutInflater inflater = Shared.activity.getLayoutInflater();
        CustomWaitingView waitView = (CustomWaitingView) inflater.inflate( R.layout.waiting_view, mainLayout, false );
        waitView.setText(R.string.personIsWaiting);
        mainLayout.addView( waitView );
        wait.setAdded( true );
        scrollDown();

    }

    public void onEvent(Die die){
        final TextView textView = new TextView(Shared.context);
        textView.setLayoutParams( Settings.WaitingViewParams );
        textView.setPadding( 30,10,20,17 );
        textView.setText(R.string.die);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        mainLayout.addView( textView );
        die.setAdded( true );
        scrollDown();
    }

    @Override
    public void onEvent(SetGameMode gameMode) {
        boolean isgame_mode = gameMode.getFast_game();
        if(isgame_mode){
            Shared.properties.setProperty( "fast_game", "on" );
        }
        else{
            Shared.properties.setProperty( "fast_game", "off" );
        }
        CustomTimer.set_mode( isgame_mode );
    }

    @Override
    public void onEvent(SetMusic setMusic) {
        if(setMusic.isEnable())
            Music.play();
        else Music.stop();
    }

    @Override
    public void onEvent(ImportantMessage importantMessage) {

    }

    @Override
    public void onPause() {
        WWProgress.saveProgress( Shared.context );
        Shared.context.startService( new Intent(Shared.context, MyServiceForGameProcess.class).putExtra( "SCHEDULE_TIME", scheduletime ) );
    }

    @Override
    public void onResume() {
        Shared.context.stopService( new Intent(Shared.context, MyServiceForGameProcess.class) );
        scrollDown();
    }

    public void scrollDown(){
        mainScrollView.post( new Runnable() {
            @Override
            public void run() {
                mainScrollView.fullScroll( ScrollView.FOCUS_DOWN );
            }
        } );
    }


}

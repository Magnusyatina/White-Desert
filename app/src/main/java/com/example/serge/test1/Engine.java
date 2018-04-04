package com.example.serge.test1;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.serge.test1.CustomEvents.AddItem;
import com.example.serge.test1.CustomEvents.CustomButton;
import com.example.serge.test1.CustomEvents.CustomEvents;
import com.example.serge.test1.CustomEvents.Die;
import com.example.serge.test1.CustomEvents.Event;
import com.example.serge.test1.CustomEvents.ImportantMessage;
import com.example.serge.test1.CustomEvents.PlayerAnwser;
import com.example.serge.test1.CustomEvents.Question;
import com.example.serge.test1.CustomEvents.Questions;
import com.example.serge.test1.CustomEvents.RemoveItem;
import com.example.serge.test1.CustomEvents.StartGame;
import com.example.serge.test1.CustomEvents.TextMessage;
import com.example.serge.test1.CustomEvents.Waiting;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

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
        ArrayList<CustomEvents> arrayList = WWProgress.addToProgress( stageId );
        gameContinue( arrayList );
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
    public void onEvent(StartGame startGame) {
        ArrayList<CustomEvents> arrayList = null;
        if((arrayList =  WWProgress.getProgressList()).size()>0)
            gameContinue( arrayList );
        else getCurrentEpisode(null);
    }

    public void onEvent(final AddItem addItem){
        final int itemId;
        if((itemId = addItem.getItem())!=-1){
            WWProgress.setItem( itemId );
            addItem.setAdded( true );
        }
    }

    //Удаление предмета из инвентаря
    public void onEvent(RemoveItem removeItem){
        final int itemId;
        if((itemId = removeItem.getItem())!=-1){
            WWProgress.unsetItem( itemId );
            removeItem.setAdded( true );
        }
    }


    //Вывод сообщения от персонажа на экран
    public void onEvent(TextMessage textMessage){
        final TextMessage message = textMessage;
        final TextView textView = new TextView( Shared.context );
        textView.setBackgroundResource( R.drawable.person_answer_background );
        textView.setPadding( 30,10,20,17 );
        textView.setTextSize((float)(Shared.context.getResources().getDimensionPixelSize( R.dimen.custom_text_size )));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        int pxSize = Shared.context.getResources().getDimensionPixelSize( R.dimen.custom_margin_top );
        layoutParams.setMargins( pxSize, pxSize,pxSize,pxSize );
        textView.setLayoutParams( layoutParams);
        textView.setText(textMessage.getText());
        mainLayout.addView(textView);
        message.setAdded(true);
        scrollDown();
    }


    public void onEvent(PlayerAnwser playerAnwser){
        TextView textView = new TextView( Shared.context );

        textView.setBackgroundResource( R.drawable.player_answer_background );

        Shared.context.getResources().getDimensionPixelSize( R.dimen.custom_margin_top );
        textView.setPadding( 30,10,20,17 );
        textView.setTextSize((float)(Shared.context.getResources().getDimensionPixelSize( R.dimen.custom_text_size )));;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        int pxSize = Shared.context.getResources().getDimensionPixelSize( R.dimen.custom_margin_top );
        layoutParams.setMargins( pxSize, pxSize,pxSize,pxSize );
        layoutParams.gravity = Gravity.RIGHT;
        textView.setLayoutParams( layoutParams);
        textView.setText( playerAnwser.getText() );

        mainLayout.addView( textView );
        playerAnwser.setAdded(true);
    }


    //Вывод вопросов на экран
    public void onEvent(Questions questions){
        final Questions quest = questions;
        ArrayList<Question> questionArray = quest.getList();
        for(Question q : questionArray){
            int itemId = q.getNeedItem();
            if(itemId == -1 || WWProgress.checkItem( itemId )){
                final CustomButton customButton = new CustomButton(Shared.context, q.getGoTo());
                customButton.setLayoutParams( Settings.questionViewParams );
                customButton.setText( q.getText() );
                customButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quest.setAdded(true);
                        PlayerAnwser playerAnwser = new PlayerAnwser();
                        playerAnwser.setText(  customButton.getText().toString() );
                        ArrayList<CustomEvents> lastStage = WWProgress.getProgressList();
                        lastStage.add( playerAnwser );
                        onEvent( playerAnwser );
                        questionView.removeAllViews();
                        scrollDown();
                        getCurrentEpisode(customButton.getGoTo());
                    }
                } );
                questionView.addView(customButton);
            }
        }
    }

    public void onEvent(Waiting waiting){
        final Waiting wait = waiting;
        final TextView waitView = new TextView( Shared.context );
        waitView.setText(R.string.personIsWaiting);
        waitView.setLayoutParams(Settings.layoutParams);
        waitView.setGravity(Gravity.CENTER_HORIZONTAL);
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
    public void onEvent(ImportantMessage importantMessage) {

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

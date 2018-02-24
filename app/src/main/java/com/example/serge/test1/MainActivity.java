package com.example.serge.test1;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.serge.test1.Objects.CustomButton;
import com.example.serge.test1.Objects.CustomEvents;
import com.example.serge.test1.Objects.PlayerAnwser;
import com.example.serge.test1.Objects.Question;
import com.example.serge.test1.Objects.Questions;
import com.example.serge.test1.Objects.TextMessage;
import com.example.serge.test1.Objects.Waiting;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public class MainActivity extends AppCompatActivity {

    LinearLayout mainLayout;
    LinearLayout questionView;
    ScrollView mainScrollView;
    String stage;
    private Handler handler = new Handler( Looper.getMainLooper() );


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MyLogInfo", " Create");
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.textArea);
        questionView = (LinearLayout) findViewById(R.id.questionsLayout);
        try {
            Scenario.loadSceanrio( this );
            Progress.loadProgress(this);
            if(Progress.list!=null)
                gameStart();
            else getCurrentEpisode();
        }catch (XmlPullParserException | IOException e){
            e.printStackTrace();
        }

    }
    protected void gameProcessed(){
        //getStringForGame();
        getCurrentEpisode();
    }
    //получение нужной стадии сценария и добавление в прогресс
    protected void getCurrentEpisode(){
        try {
            if (stage == null)
                stage = new String( "start" );
            Progress.addToProgress( stage );
            Progress.planningScheduleTime();
        }catch (NoSuchElementException e){

        }
    }

    //Продолжение игрового процесса
    protected void gameContinue(){
        getCurrentEpisode();
    }
    //Обработка прогресса, отрисовка view. НАЧАЛО ИГРОВОГО ПРОЦЕССА, ЕСЛИ ИГРА ДО ЭТОГО БЫЛА ВЫКЛЮЧЕНА
    protected void gameStart(){
            long currentTime = System.currentTimeMillis();
            //проходим по коллекции прогресса, получая объекты для дальнейшего взаимодействия
            for(CustomEvents e : Progress.list){
                    long scheduleTime = e.getScheduledtime() - currentTime;
                    if(e.getClass() == TextMessage.class){
                        TextMessage textMessage = (TextMessage) e;
                        addToViewPort( textMessage, scheduleTime );
                    }else if(e.getClass() == Waiting.class){
                        Waiting waiting = (Waiting) e;
                        addToViewPort( waiting, scheduleTime );
                    }else if(e.getClass() == Questions.class && !e.getAdded()){
                       Questions questions = (Questions) e;
                        addToViewPort( questions, scheduleTime );
                        }

                    }
                }

    }

    public void addToViewPort(TextMessage textMessage, long time){
        final TextMessage message = textMessage;
        final TextView textView = new TextView( this);
        textView.setLayoutParams(Settings.textMessageViewParams);
        textView.setText(textMessage.getText());
        if(time>=0)
            handler.postDelayed( new Runnable() {
                @Override
                public void run() {
                    mainLayout.addView(textView);
                    message.setAdded(true);
                }
            }, time );
        else mainLayout.addView(textView);
    }

    public void addToViewPort(Questions questions, long time){
        final Questions quest = questions;
        ArrayList<Question> questionArray = quest.getList();
        for(Question q : questionArray){
            final CustomButton customButton = new CustomButton(this, q.getGoTo());
            customButton.setText( q.getText() );
            customButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setStage(customButton.getGoTo());
                    quest.setAdded(true);
                    PlayerAnwser playerAnwser = new PlayerAnwser();
                    playerAnwser.setText(  customButton.getText().toString() );
                    Progress.list.add(playerAnwser);
                    addPlayerAnwserView( customButton.getText().toString() );
                    questionView.removeAllViews();
                    gameProcessed();
                }
            } );
            if(time>0){
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        questionView.addView(customButton);
                    }
                }, time );
            }else questionView.addView(customButton);;
    }

    public void addToViewPort(Waiting waiting, long time){
        final Waiting wait = waiting;
        final TextView waitView = new TextView( this );
        waitView.setText(R.string.personIsWaiting);
        waitView.setLayoutParams(Settings.layoutParams);
        waitView.setGravity(Gravity.CENTER_HORIZONTAL);
        if(time>0){
            handler.postDelayed( new Runnable() {
                @Override
                public void run() {
                    mainLayout.addView( waitView );
                    wait.setAdded( true );
                }
            }, time );

        }else mainLayout.addView( waitView );
    }

    public void addToViewPort(PlayerAnwser playerAnwser, long time){}

    public void addPlayerAnwserView(String text){
        TextView textView = new TextView( this );
        textView.setText( text );
        textView.setGravity( Gravity.RIGHT );
        mainLayout.addView(textView);

    }

    public void onPause(){
        super.onPause();
        Log.i("MyLogInfo", " Pause");
        Progress.saveProgress(this);
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i("MyLogInfo", " Destroy");
    }
    public void onResume(){
        super.onResume();
        Log.i("MyLogInfo", " Resume");
    }



    public String getStage(){
        return stage;
    }
    public void setStage(String stage){
        this.stage = stage;
    }
    public ScrollView getMainScrollView(){
        return  mainScrollView;
    }
    public LinearLayout getMainLayout(){
        return mainLayout;
    }
}

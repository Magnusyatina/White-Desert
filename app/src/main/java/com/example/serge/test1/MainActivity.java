package com.example.serge.test1;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;
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
import android.view.WindowManager;
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
    private long scheduletime = 0;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        Log.i("MyLogInfo", " Create");
        setContentView(R.layout.activity_main);
        mainScrollView = (ScrollView) findViewById(R.id.mainScrollView);
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
            ArrayList<CustomEvents> events = Progress.addToProgress( stage );
            gameContinue(events);
        }catch (NoSuchElementException e){

        }
    }

    //Продолжение игрового процесса
    protected void gameContinue(ArrayList<CustomEvents> events){
        long currentTime = System.currentTimeMillis();
        long timer = 0, scheduleTime = 0;
        //проходим по коллекции прогресса, получая объекты для дальнейшего взаимодействия
        for(CustomEvents e : events){
            scheduleTime = e.getScheduledtime();
            timer = scheduleTime - currentTime;
            if(e.getClass() == TextMessage.class){
                TextMessage textMessage = (TextMessage) e;
                addToViewPort( textMessage, timer );
            }else if(e.getClass() == Waiting.class){
                Waiting waiting = (Waiting) e;
                addToViewPort( waiting, timer );
            }else if(e.getClass() == Questions.class){
                Questions questions = (Questions) e;
                addToViewPort( questions, timer );
            }

        }
        this.scheduletime = scheduleTime;
    }
    //Обработка прогресса, отрисовка view. НАЧАЛО ИГРОВОГО ПРОЦЕССА, ЕСЛИ ИГРА ДО ЭТОГО БЫЛА ВЫКЛЮЧЕНА
    protected void gameStart(){
        long currentTime = System.currentTimeMillis();
        long timer = 0, scheduleTime = 0;
            //проходим по коллекции прогресса, получая объекты для дальнейшего взаимодействия
            for(CustomEvents e : Progress.list){
                scheduleTime = e.getScheduledtime();
                timer = scheduleTime - currentTime;
                if(e.getClass() == TextMessage.class){
                    TextMessage textMessage = (TextMessage) e;
                    addToViewPort( textMessage, timer );
                }else if(e.getClass() == Waiting.class){
                    Waiting waiting = (Waiting) e;
                    addToViewPort( waiting, timer );
                }else if(e.getClass() == Questions.class && !e.getAdded()){
                    Questions questions = (Questions) e;
                    addToViewPort( questions, timer );
                }else if(e.getClass() == PlayerAnwser.class){
                    PlayerAnwser playerAnwser = (PlayerAnwser) e;
                    addToViewPort( playerAnwser );
                }
            }
        this.scheduletime = scheduleTime;
    }



    public void addToViewPort(TextMessage textMessage, long time){
        final TextMessage message = textMessage;
        final TextView textView = new TextView( this);
       // int id = getResources().getIdentifier( "dialogmesgdpi", "drawable", getPackageName() );
        textView.setBackgroundResource( R.drawable.dialogbg );
        textView.setPadding( 30,10,20,17 );
        textView.setTextColor( Color.WHITE );
        textView.setLayoutParams(Settings.textMessageViewParams);
        textView.setText(textMessage.getText());
        if(time>=0)
            handler.postDelayed( new Runnable() {
                @Override
                public void run() {
                    mainLayout.addView(textView);
                    message.setAdded(true);
                    scrollDown();
                }
            }, time );
        else {
            mainLayout.addView(textView);
            message.setAdded(true);
        }
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
                    addToViewPort( playerAnwser );
                    questionView.removeAllViews();
                    scrollDown();
                    getCurrentEpisode();
                }
            } );
            if(time>0){
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        questionView.addView(customButton);
                    }
                }, time );
            }else {
                questionView.addView(customButton);
            };
         }
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
                    scrollDown();
                }
            }, time );

        }else {
            mainLayout.addView( waitView );
            wait.setAdded( true );
        };
    }

    public void addToViewPort(PlayerAnwser playerAnwser){
        TextView textView = new TextView( this );
        textView.setBackgroundResource( R.drawable.dialoganwserbg );
        textView.setLayoutParams( Settings.textAnwserViewParams );
        textView.setPadding( 30,10,20,17 );
        textView.setText( playerAnwser.getText() );
        textView.setGravity( Gravity.RIGHT );
        mainLayout.addView( textView );
        playerAnwser.setAdded(true);
    }

    public void scrollDown(){
        mainScrollView.post( new Runnable() {
            @Override
            public void run() {
                mainScrollView.fullScroll( ScrollView.FOCUS_DOWN );
            }
        } );
    }

    public void onPause(){
        super.onPause();
        Log.i("MyLogInfo", " Pause");
        Progress.saveProgress(this);
        startService( new Intent(this, MyServiceForGameProcess.class).putExtra( "SCHEDULE_TIME", scheduletime ) );
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i("MyLogInfo", " Destroy");
    }
    public void onResume(){
        super.onResume();
        stopService( new Intent(this, MyServiceForGameProcess.class) );
        scrollDown();
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

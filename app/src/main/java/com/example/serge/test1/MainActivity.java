package com.example.serge.test1;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.shapes.Shape;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.serge.test1.Objects.AddItem;
import com.example.serge.test1.Objects.CustomButton;
import com.example.serge.test1.Objects.CustomEvents;
import com.example.serge.test1.Objects.Die;
import com.example.serge.test1.Objects.PlayerAnwser;
import com.example.serge.test1.Objects.Question;
import com.example.serge.test1.Objects.Questions;
import com.example.serge.test1.Objects.RemoveItem;
import com.example.serge.test1.Objects.Stage;
import com.example.serge.test1.Objects.TextMessage;
import com.example.serge.test1.Objects.Waiting;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


public class MainActivity extends AppCompatActivity {

    LinearLayout mainLayout;
    LinearLayout questionView;
    ScrollView mainScrollView;
    LinearLayout inventory;
    String stage;
    private Handler handler = new Handler( Looper.getMainLooper() );
    private MediaPlayer mediaPlayer = null;
    private long scheduletime = 0;
    private ListView mDrawerListView;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setVolumeControlStream( AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_main);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        mDrawerListView.setAdapter( new ArrayAdapter<String>(this, R.layout.drawer_list_item, getResources().getStringArray(R.array.menu_items) ) );
        setMusic();
        mainScrollView = (ScrollView) findViewById(R.id.mainScrollView);
        mainLayout = (LinearLayout) findViewById(R.id.textArea);
        questionView = (LinearLayout) findViewById(R.id.questionsLayout);
        try {
            Scenario.loadSceanrio( this );
            Progress.loadProgress(this);
            if(Progress.progressList.size()!=0)
                gameStart();
            else getCurrentEpisode();
        }catch (XmlPullParserException | IOException e){
            e.printStackTrace();
        }

    }

    protected void setMusic(){
        mediaPlayer = MediaPlayer.create(this, R.raw.soundtrack);
        mediaPlayer.setVolume( 0.05f, 0.05f );
        mediaPlayer.setLooping(true);
    }

    protected void unsetMusic(){

    }


    //получение нужной стадии сценария и добавление в прогресс
    protected void getCurrentEpisode(){
        try {
            if (stage == null)
                stage = new String( "start" );
            ArrayList<CustomEvents> arrayList = Progress.addToProgress(stage);
            gameContinue(arrayList);

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
            }else if(e.getClass() == Questions.class && !e.getAdded()){
                Questions questions = (Questions) e;
                addToViewPort( questions, timer );
            }else if(e.getClass() == PlayerAnwser.class){
                PlayerAnwser playerAnwser = (PlayerAnwser) e;
                addToViewPort( playerAnwser );
            }else if(e.getClass() == AddItem.class && !e.getAdded()){
                AddItem item = (AddItem) e;
                addItem( item, timer );
            }else if(e.getClass() == RemoveItem.class && !e.getAdded()){
                RemoveItem item = (RemoveItem) e;
                removeItem( item, timer );
            }else if(e.getClass() == Die.class){
                Die die = (Die) e;
                addToViewPort( die, timer );
                break;
            }

        }
        this.scheduletime = scheduleTime;
    }
    //Обработка прогресса, отрисовка view. НАЧАЛО ИГРОВОГО ПРОЦЕССА, ЕСЛИ ИГРА ДО ЭТОГО БЫЛА ВЫКЛЮЧЕНА
    protected void gameStart(){
        ArrayList<CustomEvents> currStage = null;
        for(Map.Entry<String, ArrayList<CustomEvents>> item : Progress.progressList.entrySet()){
            currStage = item.getValue();
            gameContinue( currStage );
        }
    }

    private void addItem(final AddItem addItem, long time){
        final int itemId;
        if((itemId = addItem.getItem())!=-1){
            if(time>0){
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        Progress.person.setItem( itemId );
                        addItem.setAdded( true );
                    }
                }, time );
            }else {
                Progress.person.setItem( itemId );
                addItem.setAdded( true );
            };
        }
    }
    private void removeItem(final RemoveItem removeItem, long time){
        final int itemId;
        if((itemId = removeItem.getItem())!=-1){
            if(time>0){
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        Progress.person.unsetItem( itemId );
                        removeItem.setAdded( true );
                    }
                },time );
            }else {
                Progress.person.unsetItem( itemId );
                removeItem.setAdded( true );
                };
        }
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
            int itemId = q.getNeedItem();
            if(itemId == -1 || Progress.person.checkItem( itemId )){
                final CustomButton customButton = new CustomButton(this, q.getGoTo());
                customButton.setLayoutParams( Settings.questionViewParams );
                customButton.setText( q.getText() );
                customButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setStage(customButton.getGoTo());
                        quest.setAdded(true);
                        PlayerAnwser playerAnwser = new PlayerAnwser();
                        playerAnwser.setText(  customButton.getText().toString() );
                        ArrayList<CustomEvents> lastStage = Progress.progressList.getTail();
                        if(lastStage!=null){
                            lastStage.add( playerAnwser );
                            addToViewPort( playerAnwser );
                            questionView.removeAllViews();
                            scrollDown();
                            getCurrentEpisode();
                        }
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

    public void addToViewPort(final Die die, long time){
        final TextView textView = new TextView(this);
        textView.setLayoutParams( Settings.WaitingViewParams );
        textView.setPadding( 30,10,20,17 );
        textView.setText(R.string.die);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        if(time > 0){
            handler.postDelayed( new Runnable() {
                @Override
                public void run() {
                    mainLayout.addView( textView );
                    die.setAdded( true );
                    scrollDown();
                }
            }, time );
        }else {
            mainLayout.addView( textView );
            die.setAdded( true );
        }
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
        if(mediaPlayer!=null)
            mediaPlayer.pause();
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
        if(mediaPlayer!=null)
            mediaPlayer.start();
    }

    public void switchBag(View view){

        LinearLayout container = (LinearLayout) findViewById( R.id.ContainsItem );
        if(inventory == null)
            createInventory();
        if(inventory.getVisibility() == View.INVISIBLE){
            inventory.setVisibility( View.VISIBLE );
            container = (LinearLayout) findViewById( R.id.ContainsItem );
            if(Progress.person.checkItem( 1 )){
                ImageView imgeView = new ImageView(this);
                imgeView.setBackgroundResource( R.drawable.matches2 );
                imgeView.setLayoutParams( Settings.item );
                container.addView( imgeView );

            }
            if(Progress.person.checkItem( 2 )){
                ImageView imgeView = new ImageView(this);
                imgeView.setBackgroundResource( R.drawable.map2 );
                imgeView.setLayoutParams( Settings.item );
                container.addView( imgeView );
            }
            if(Progress.person.checkItem( 3 )){
                ImageView imgeView = new ImageView(this);
                imgeView.setBackgroundResource( R.drawable.flashlight2 );
                imgeView.setLayoutParams( Settings.item );
                container.addView( imgeView );
            }


        }else {
            inventory.setVisibility( View.INVISIBLE );
            container.removeAllViews();
        };
    }

    private void createInventory(){
        inventory = (LinearLayout) findViewById( R.id.inventory );
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) inventory.getLayoutParams();
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize( point );
        params.setMargins( (int)(point.x*0.1), (int)(point.y*0.2),0,0 );
        params.height = (int) (point.y*0.2);
        params.width = (int) (point.x*0.8);
        inventory.setLayoutParams( params );
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

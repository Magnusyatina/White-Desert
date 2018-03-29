package com.example.serge.test1;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serge.test1.Objects.AddItem;
import com.example.serge.test1.Objects.CustomButton;
import com.example.serge.test1.Objects.CustomEvents;
import com.example.serge.test1.Objects.Die;
import com.example.serge.test1.Objects.PlayerAnwser;
import com.example.serge.test1.Objects.Question;
import com.example.serge.test1.Objects.Questions;
import com.example.serge.test1.Objects.RemoveItem;
import com.example.serge.test1.Objects.TextMessage;
import com.example.serge.test1.Objects.Waiting;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    LinearLayout mainLayout;
    LinearLayout questionView;
    ScrollView mainScrollView;
    LinearLayout inventory;
    String stage;
    private Handler handler = new Handler( Looper.getMainLooper() );
    private NavigationView navView;
    private MediaPlayer mediaPlayer = null;
    private long scheduletime = 0;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Установка полноэкранного режима
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        //Настройка звука
        setVolumeControlStream( AudioManager.STREAM_MUSIC);


        setContentView(R.layout.activity_main);

        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener( this );
        SwitchCompat switchCompat = (SwitchCompat) navView.getMenu().getItem( 3 ).getActionView();


        //Установка звукового сопровождения
        setMusic();
        mainScrollView = (ScrollView) findViewById(R.id.mainScrollView);
        mainLayout = (LinearLayout) findViewById(R.id.textArea);
        questionView = (LinearLayout) findViewById(R.id.questionsLayout);
        try {

            //Вызов функции загрузки сценария из файла
            Scenario.loadSceanrio( this );

            //Вызов функции загрузки прогресса
            WWProgress.loadProgress(this);
            if(WWProgress.getProgressList().size()!=0)
                //Если коллекция прогресса не пуста, вызывается функция обработки прогресса
                gameStart();
            //Иначе вызывается функция получения текущей стадии
            else getCurrentEpisode();
        }catch (XmlPullParserException | IOException e){
            e.printStackTrace();
        }

    }

    protected void setMusic(){
        //Создание плеера
        mediaPlayer = MediaPlayer.create(this, R.raw.soundtrack);

        //Установка параметра громкости звучания
        mediaPlayer.setVolume( 0.05f, 0.05f );

        //Установка зацикливания звуковой дорожки
        mediaPlayer.setLooping(true);
    }

    protected void unsetMusic(){

    }


    //получение нужной стадии сценария и добавление в прогресс
    protected void getCurrentEpisode(){
        try {
            if (stage == null)
                //Если переменная стадии null, присвается идентификатор начала сценария
                stage = new String( "start" );

            //Вызов функции добавления в прогресс событий, относящихся к определенной стадии и возврат этих событий
            ArrayList<CustomEvents> arrayList = WWProgress.addToProgress(stage);
            //Вызов функции обработки событий
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
        ArrayList<CustomEvents> currStage = WWProgress.getProgressList();
        gameContinue( currStage );

    }


    //Добавление предмета в инвентарь
    private void addItem(final AddItem addItem, long time){
        final int itemId;
        if((itemId = addItem.getItem())!=-1){
            if(time>0){
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        WWProgress.setItem( itemId );
                        addItem.setAdded( true );
                    }
                }, time );
            }else {
                WWProgress.setItem( itemId );
                addItem.setAdded( true );
            };
        }
    }

    //Удаление предмета из инвентаря
    private void removeItem(final RemoveItem removeItem, long time){
        final int itemId;
        if((itemId = removeItem.getItem())!=-1){
            if(time>0){
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        WWProgress.unsetItem( itemId );
                        removeItem.setAdded( true );
                    }
                },time );
            }else {
                WWProgress.unsetItem( itemId );
                removeItem.setAdded( true );
                };
        }
    }


    //Вывод сообщения от персонажа на экран
    public void addToViewPort(TextMessage textMessage, long time){
        final TextMessage message = textMessage;
        final TextView textView = new TextView( this);
        textView.setBackgroundResource( R.drawable.person_answer_background );
        textView.setPadding( 30,10,20,17 );
        textView.setTextSize((float)(getResources().getDimensionPixelSize( R.dimen.custom_text_size )));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        int pxSize = getResources().getDimensionPixelSize( R.dimen.custom_margin_top );
        layoutParams.setMargins( pxSize, pxSize,pxSize,pxSize );
        textView.setLayoutParams( layoutParams);

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


    public void addToViewPort(PlayerAnwser playerAnwser){
        TextView textView = new TextView( this );

        textView.setBackgroundResource( R.drawable.player_answer_background );

        getResources().getDimensionPixelSize( R.dimen.custom_margin_top );
        textView.setPadding( 30,10,20,17 );
        textView.setTextSize((float)(getResources().getDimensionPixelSize( R.dimen.custom_text_size )));;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        int pxSize = getResources().getDimensionPixelSize( R.dimen.custom_margin_top );
        layoutParams.setMargins( pxSize, pxSize,pxSize,pxSize );
        layoutParams.gravity = Gravity.RIGHT;
        textView.setLayoutParams( layoutParams);

        textView.setText( playerAnwser.getText() );

        mainLayout.addView( textView );
        playerAnwser.setAdded(true);
    }


    //Вывод вопросов на экран
    public void addToViewPort(Questions questions, long time){
        final Questions quest = questions;
        ArrayList<Question> questionArray = quest.getList();
        for(Question q : questionArray){
            int itemId = q.getNeedItem();
            if(itemId == -1 || WWProgress.checkItem( itemId )){
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
                        ArrayList<CustomEvents> lastStage = WWProgress.getProgressList();
                        lastStage.add( playerAnwser );
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
        WWProgress.saveProgress(this);
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
            if(WWProgress.checkItem( 1 )){
                ImageView imgeView = new ImageView(this);
                imgeView.setBackgroundResource( R.drawable.matches2 );
                imgeView.setLayoutParams( Settings.item );
                container.addView( imgeView );

            }
            if(WWProgress.checkItem( 2 )){
                ImageView imgeView = new ImageView(this);
                imgeView.setBackgroundResource( R.drawable.map2 );
                imgeView.setLayoutParams( Settings.item );
                container.addView( imgeView );
            }
            if(WWProgress.checkItem( 3 )){
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




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.new_game: Toast.makeText( this, "Новая игра", Toast.LENGTH_SHORT ).show(); start_new_game(); break;
            case R.id.about: Toast.makeText( this, "Справка", Toast.LENGTH_SHORT ).show(); break;
            case R.id.game_condition_switch: Toast.makeText( this, "1 свитч", Toast.LENGTH_SHORT ).show(); break;
            case R.id.music_switch: Toast.makeText( this, "2 свитч", Toast.LENGTH_SHORT ).show(); break;
            default: break;
        }
        return true;
    }

    public void start_new_game(){
        AlertDialog.Builder ad = new AlertDialog.Builder( this );

        ad.setMessage( R.string.question_about_the_new_game )
                .setCancelable( false )
                .setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WWProgress.dump_of_progress();
                        stage = null;
                        mainLayout.removeAllViews();
                        questionView.removeAllViews();
                        getCurrentEpisode();
                        dialog.cancel();
                    }
                } )
                .setNegativeButton( R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );
        ad.show();

    }


    public void setStage(String stage){
        this.stage = stage;
    }
}

package com.example.serge.test1;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    LinearLayout mainLayout;
    ScrollView mainScrollView;
    String stage;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testscroll);
        mainLayout = (LinearLayout) findViewById(R.id.mainlayout);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        try {
            Scenario.loadSceanrio( this );
            gameProcessed();
        }catch (XmlPullParserException | IOException e){
            e.printStackTrace();
        }

    }
    protected void gameProcessed(){
        getStringForGame();

    }
    protected void getStringForGame(){
        if(stage==null)
          stage = new String("start");
        int id = this.getResources().getIdentifier(stage, "array", this.getPackageName());
        if(id != 0){
            String[] s = getResources().getStringArray(id);
            addTextView(s);
        }
    }

    public void addTextView(final String s[]){
        final Handler handler = new Handler( Looper.getMainLooper());
        int length = s.length;
        for(int i = 0; i<=length;i++)
        {
            if(i!=length){
                final CustomTextView customTextView = new CustomTextView( this );
                if(s[i].indexOf("/")!=-1){
                    String[] teststr = s[i].split("/",2);
                    s[i] = teststr[0];
                    final TextView waitView = new TextView(this);
                    waitView.setText("Джозеф занят");
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    waitView.setLayoutParams(layoutParams);
                    waitView.setGravity(Gravity.CENTER_HORIZONTAL);
                    handler.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            mainLayout.addView( waitView );
                        }
                    }, CustomTimer.getValue() );
                    CustomTimer.addTime(teststr[1]);
                }
                customTextView.setText( s[i] );
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0,50,0,50);
                        customTextView.setLayoutParams( layoutParams );
                        mainLayout.addView(customTextView);
                    }
                }, CustomTimer.getValue());
            }
            else{ //final CreateButton answer = new CreateButton();
                int id = this.getResources().getIdentifier(stage+"_questions", "array", this.getPackageName());
                if(id != 0){
                    String str[] = getResources().getStringArray(id);
                    final LinearLayout answerLayout = CreateButton.crAnswer(this, str[0],str[1]);
                    handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        getMainLayout().addView(answerLayout);
                    }
                }, CustomTimer.getValue() );
                }else{
                        final Button but = CreateButton.getRestartButton(this);
                        handler.postDelayed( new Runnable() {
                            @Override
                            public void run() {
                                getMainLayout().addView(but);
                            }
                        }, CustomTimer.getValue() );
                    }
            }
            CustomTimer.addTime();
        }
        CustomTimer.clearTimer();
    }

    public String getStage(){
        return stage;
    }
    public void setStage(String stage){
        this.stage = stage;
    }
    public void firstButtonClick(View view){
        onClickActive(0);
    }
    public void secondButtonClick(View view){
        onClickActive(1);
    }
    public void onClickActive(int n){
        int id = this.getResources().getIdentifier("logic_"+getStage(), "array", this.getPackageName());
        if(id != 0){
            String[] logicStage = this.getResources().getStringArray(id);
           // MyServiceForGameProcess service = new MyServiceForGameProcess();
           // startService(new Intent(this, service.getClass()));
            setStage(logicStage[n]);
            mainLayout.removeViewAt(mainLayout.getChildCount()-1);
            gameProcessed();
        }
    }

    public void restartGame(View view){
        stage = null;
        mainLayout.removeAllViews();
        gameProcessed();
    }

    public ScrollView getMainScrollView(){
        return  mainScrollView;
    }
    public LinearLayout getMainLayout(){
        return mainLayout;
    }
}

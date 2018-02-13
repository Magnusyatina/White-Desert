package com.example.serge.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;



/**
 * Created by serge on 06.09.2017.
 */

public class CreateButton {
    public static Button anwserone;
    public static Button anwsertwo;

    public static LinearLayout crAnswer(Context context, String s1, String s2){
        LinearLayout answerlayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.answerlayout, null);
        Button button1 = (Button) LayoutInflater.from(context).inflate(R.layout.firstbutton, answerlayout, false);
        Button button2 = (Button) LayoutInflater.from(context).inflate(R.layout.secondbutton, answerlayout, false);
        button1.setText(s1);
        button2.setText(s2);
        anwserone = button1;
        anwsertwo = button2;
        answerlayout.addView(button1);
        answerlayout.addView(button2);
        return answerlayout;
    }

    public static Button getRestartButton(Context context){
        Button but = (Button) LayoutInflater.from(context).inflate( R.layout.restartbutton, null );
        but.setText("Начать заново");
        return  but;
    }

    public static Button getAnswerone(){
        return  anwserone;
    }
    public static Button getAnswertwo(){
        return  anwsertwo;
    }
    public static void clearCurrentButtons(){
        anwserone = null;
        anwsertwo = null;
    }
}

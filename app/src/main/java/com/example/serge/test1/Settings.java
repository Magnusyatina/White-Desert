package com.example.serge.test1;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by sergey37192 on 21.02.2018.
 */

public class Settings {
    public static LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public static LinearLayout.LayoutParams textMessageViewParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public static LinearLayout.LayoutParams textAnwserViewParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public static LinearLayout.LayoutParams WaitingViewParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public static boolean FAST_GAME = false;

    static{
        textMessageViewParams.setMargins(15,50,15,50);
        textAnwserViewParams.setMargins(15,50,15,50);
        textAnwserViewParams.gravity = Gravity.RIGHT;


    }
}
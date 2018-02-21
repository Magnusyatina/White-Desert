package com.example.serge.test1.Objects;

import android.content.Context;
import android.widget.Button;

/**
 * Created by sergey37192 on 21.02.2018.
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {
    private String goTo = null;

    public CustomButton(Context context, String goTo) {
        super( context );
        this.goTo = goTo;
    }

    public String getGoTo(){
        return goTo;
    }
}

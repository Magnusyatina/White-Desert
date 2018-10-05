package com.example.serge.test1.CustomView;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serge.test1.CustomEvents.TacticalEvent;
import com.example.serge.test1.R;

import java.util.zip.Inflater;

public class TacticalView extends LinearLayout{
    TextView textView = null;
    LinearLayout choices_container = null;

    TacticalEvent mainNode = null;
    Handler handler = null;

    public TacticalView(Context context) {
        super(context);
    }

    public TacticalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TacticalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TacticalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setNode(TacticalEvent mainNode){
        this.mainNode = mainNode;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        textView = (TextView) findViewById(R.id.testdialogview);
        choices_container = (LinearLayout) findViewById(R.id.choices_container);
    }
}

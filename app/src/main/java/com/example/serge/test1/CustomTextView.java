package com.example.serge.test1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by serge on 02.10.2017.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    private String text;
    private Paint paint = new Paint();
    public CustomTextView(Context context) {
        super(context);
        paint.setTextSize(16);
        paint.setColor(Color.BLACK);
        paint.setTextSize(28);
    }

    public void setText(String text){
        this.text = text;
    }


    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawText(text,20,20,paint);
    }
}

package org.magnusario.whitedesert.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by sergey37192 on 08.04.2018.
 */

public class CustomButtonPlayerAnswer extends android.support.v7.widget.AppCompatTextView {

    public CustomButtonPlayerAnswer(Context context) {
        super(context);

    }

    public CustomButtonPlayerAnswer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButtonPlayerAnswer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
        Log.d("MY", "animation start");
    }
}

package org.magnusario.whitedesert.view;

import android.content.Context;
import android.util.AttributeSet;

public class CustomButton extends android.support.v7.widget.AppCompatButton {
    private String goTo = null;

    public CustomButton(Context context) {
        super(context);
    }

    public void setGoTo(String string) {
        this.goTo = string;
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getGoTo() {
        return goTo;
    }


}

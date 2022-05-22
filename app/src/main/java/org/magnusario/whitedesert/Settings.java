package org.magnusario.whitedesert;

import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Settings {
    public static LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public static LinearLayout.LayoutParams questionViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public static LinearLayout.LayoutParams WaitingViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public static LinearLayout.LayoutParams item = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public static boolean FAST_GAME = false;


    static {
        questionViewParams.setMargins(25, 25, 25, 0);
        item.setMargins(50, 0, 0, 0);
    }
}

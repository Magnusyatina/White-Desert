package org.magnusario.whitedesert.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.magnusario.whitedesert.R;

public class CustomHintDiaolog extends android.support.v4.app.DialogFragment {
    private String text = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hint_layout, container, false);
        ((TextView) view.findViewById(R.id.hint_text)).setText(text);
        return view;
    }

    public void setText(String text) {
        this.text = text;
    }


}

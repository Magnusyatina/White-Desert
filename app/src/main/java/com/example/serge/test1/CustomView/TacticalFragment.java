package com.example.serge.test1.CustomView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serge.test1.CustomEvents.TacticalChildNode;
import com.example.serge.test1.CustomEvents.TacticalEvent;
import com.example.serge.test1.R;
import com.example.serge.test1.Shared;

public class TacticalFragment extends android.support.v4.app.Fragment {
    TextView textView = null;
    LinearLayout choices_container = null;

    ViewGroup container = null;

    TacticalEvent mainNode = null;
    Handler handler = null;

    public TacticalFragment(){
        super();
        handler = Shared.eventPool.getHandler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.testing_dialog_fragment, container, false );
        textView = (TextView) view.findViewById( R.id.testdialogview );
        choices_container = (LinearLayout) view.findViewById( R.id.choices_container );
        this.container = container;
        container.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeText();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.FragmentContainer).setBackgroundColor(R.color.colorFragmentBg);

    }

    public void setNode(TacticalEvent mainNode){
        this.mainNode = mainNode;
    }

    public void changeText(){
        TacticalChildNode chNode = mainNode.getCurrNode();
        if(chNode == null)
            return;

        String text = chNode.getText();
        if (text == null)
            return;

        char[] ch = text.toCharArray();
        for(int i = 0; i<ch.length; i++){
            final char symbol = ch[i];
            textView.postDelayed( new Runnable() {
                @Override
                public void run() {
                    textView.append( Character.toString(symbol) );
                }
            }, i*25 );
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        container.setVisibility(View.INVISIBLE);

    }
}

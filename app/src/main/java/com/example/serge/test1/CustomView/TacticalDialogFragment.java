package com.example.serge.test1.CustomView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serge.test1.CustomEvents.TacticalChildNode;
import com.example.serge.test1.CustomEvents.TacticalEvent;
import com.example.serge.test1.R;
import com.example.serge.test1.Shared;

import java.util.Map;
import java.util.TreeMap;
import java.util.zip.Inflater;

public class TacticalDialogFragment extends DialogFragment {
    TextView textView = null;
    LinearLayout choices_container = null;
    TacticalEvent mainNode = null;

    public void setMainNode(TacticalEvent mainNode){
        this.mainNode = mainNode;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.testing_dialog_fragment, null );
        textView = (TextView) view.findViewById( R.id.testdialogview );
        choices_container = (LinearLayout) view.findViewById( R.id.choices_container );
        builder.setView( view );
        textView.post( new Runnable() {
            @Override
            public void run() {
                changeText();
            }
        } );

        return builder.create();

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
            }, i*40 );
        }

        TreeMap<String, String> map = chNode.getChoices();
        if(map == null)
            return;

        for(Map.Entry<String, String> iterator : map.entrySet()){
            String choice_text = iterator.getKey();
            Button button = (Button) LayoutInflater.from( Shared.context ).inflate( R.layout.choice_button,choices_container, false );
            button.setText( choice_text );
            choices_container.addView( button );
        }

    }

}

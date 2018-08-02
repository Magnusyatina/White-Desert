package com.example.serge.test1.CustomView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.serge.test1.R;

import java.util.zip.Inflater;

public class TacticalDialogFragment extends DialogFragment {
    TextView textView = null;
    String text = "Привет, мой друг! Я новая фича";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.testing_dialog_fragment, null );
        textView = (TextView) view.findViewById( R.id.testdialogview );
        builder.setView( view );

        return builder.create();

    }

    public void changeText(){
        char[] ch = text.toCharArray();
        for(int i = 0; i<ch.length; i++){
            final char symbol = ch[i];
            textView.postDelayed( new Runnable() {
                @Override
                public void run() {
                    textView.append( Character.toString(symbol) );
                }
            }, i*100 );
        }
    }

}

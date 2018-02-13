package com.example.serge.test1;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.serge.test1.Objects.Messages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Created by sergey37192 on 28.01.2018.
 */

public class Progress {


    LinkedList<Messages> list;



    public void loadProgress(){
        File f = new File("saved.dat");
        if(f.exists()){
            try {
                ObjectInputStream in = new ObjectInputStream( new FileInputStream( f ) );
                list = (LinkedList<Messages>) in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveProgress(){
        try {
            ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(new File("saved.dat")));
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Messages> getList(){
        return list;
    }

}

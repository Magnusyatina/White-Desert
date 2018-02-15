package com.example.serge.test1;

import android.content.Context;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.serge.test1.Objects.CustomEvents;
import com.example.serge.test1.Objects.Messages;
import com.example.serge.test1.Objects.TextMessage;
import com.example.serge.test1.Objects.Waiting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by sergey37192 on 28.01.2018.
 */

public class Progress {


    public static ArrayList<CustomEvents> list;



    public static void loadProgress(){
        File f = new File("saved.dat");
        if(f.exists()){
            try {
                ObjectInputStream in = new ObjectInputStream( new FileInputStream( f ) );
                list = (ArrayList<CustomEvents>) in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static void saveProgress(){
        try {
            ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(new File("saved.dat")));
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToProgress(String stage){
        if(list == null)
            list = new ArrayList<>();
        ArrayList<CustomEvents> EventList = null;
        if((EventList = Scenario.scenario.get(stage))!=null){
            for(CustomEvents e : EventList){
               list.add( e );
            }
        }
    }

}

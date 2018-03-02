package com.example.serge.test1;

import android.content.Context;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.serge.test1.Objects.CustomEvents;
import com.example.serge.test1.Objects.Messages;
import com.example.serge.test1.Objects.TextMessage;
import com.example.serge.test1.Objects.Waiting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by sergey37192 on 28.01.2018.
 */

public class Progress {


    public static ArrayList<CustomEvents> list = null;
    public static String hell= "hell";



    public static void loadProgress(Context context){
        File file = new File(context.getFilesDir(), "save.dat");
        if(file.exists())
        try {
            ObjectInputStream obj = new ObjectInputStream( new FileInputStream( file ) );
            list = (ArrayList<CustomEvents>) obj.readObject();
            obj.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* try {
                ObjectInputStream in = new ObjectInputStream( new FileInputStream( f ) );
                list = (ArrayList<CustomEvents>) in.readObject();
                Log.i("MyLogInfo", "List: "+list.size());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }*/

    }
    public static void saveProgress(Context context){

        try {
            if(list!=null){
               /* File file = new File(context.getFilesDir(), "save.dat");
                if(!file.exists())
                    file.createNewFile();*/

                FileOutputStream fout = context.openFileOutput("save.dat", Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream( fout);
                out.writeObject( list );
                out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


           /* File saveFile = new File(context.getFilesDir(), "Saves/save.dat");

            Log.i("MyLogInfo", saveFile.getAbsolutePath());
        try {
            ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(saveFile));
            out.writeObject(list);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //

    }

    public static ArrayList<CustomEvents> addToProgress(String stage){
        if(list == null)
            list = new ArrayList<>();
        ArrayList<CustomEvents> EventList = null;
        if((EventList = (ArrayList<CustomEvents>) Scenario.scenario.get(stage))!=null){
            ArrayList<CustomEvents> re = new ArrayList<>(  );
            for(CustomEvents e : EventList){
                try {
                    CustomEvents item = (CustomEvents) e.clone();
                    planningScheduleTime( item );
                    re.add(item);
                } catch (CloneNotSupportedException e1) {

                }
            }
            list.addAll( re );
            CustomTimer.clearTimer();
            return re;
        }else throw new NoSuchElementException();
    }

    public static void planningScheduleTime(CustomEvents item){
        item.setScheduledtime( CustomTimer.getTestValue() );
        if(item.getClass() == Waiting.class){
            Waiting waiting = (Waiting) item;
            CustomTimer.addTestTime( waiting.getValue() );
        }
    }

    public static void planningScheduleTime(){
        if(list!=null&&list.size()!=0){
            for(CustomEvents e : list){
                if(!e.getAdded()){
                    e.setScheduledtime(CustomTimer.getTestValue());
                    if(e.getClass() == Waiting.class) {
                        Waiting waiting = (Waiting) e;
                      //  list.addAll( new ArrayList<CustomEvents>( );
                        CustomTimer.addTestTime(waiting.getValue());
                    }
                }
            }
            CustomTimer.clearTimer();
        }
    }

}

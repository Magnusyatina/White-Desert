package com.example.serge.test1;

import android.content.Context;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.serge.test1.Objects.CustomEvents;
import com.example.serge.test1.Objects.Messages;
import com.example.serge.test1.Objects.Stage;
import com.example.serge.test1.Objects.TextMessage;
import com.example.serge.test1.Objects.Waiting;
import com.example.serge.test1.Person.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by sergey37192 on 28.01.2018.
 */

public class Progress {

    public static LinkedHashMap<String, Stage> progressList = null;
    public static Person person = null;


    public static String hell= "hell";



    public static void loadProgress(Context context){
        File file = new File(context.getFilesDir(), "save.dat");
        if(file.exists())
        try {
            ObjectInputStream obj = new ObjectInputStream( new FileInputStream( file ) );
            progressList = (LinkedHashMap<String, Stage>) obj.readObject();
            obj.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void saveProgress(Context context){

        try {
            if(progressList!=null){
                FileOutputStream fout = context.openFileOutput("save.dat", Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream( fout);
                out.writeObject( progressList );
                out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static Stage addToProgress(String stage_name){
        if(progressList == null)
            progressList = new LinkedHashMap<>(  );
        Stage stage = null;
        ArrayList<CustomEvents> EventList = null;
        if((stage = (Stage) Scenario.scenarioList.get( stage_name ))!=null){
            Stage newStage = null;
            try {
                newStage = (Stage) stage.clone();
                EventList = newStage.getArray();
                for(CustomEvents e : EventList){
                    planningScheduleTime( e );
                }
                progressList.put( stage_name, stage );
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            CustomTimer.clearTimer();
            return newStage;
        }else throw new NoSuchElementException();
    }

    public static void planningScheduleTime(CustomEvents item){
        item.setScheduledtime( CustomTimer.getTestValue() );
        if(item.getClass() == Waiting.class){
            Waiting waiting = (Waiting) item;
            CustomTimer.addTestTime( waiting.getValue() );
        }
    }

    /*public static void planningScheduleTime(){
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
    }*/

    public static Stage getLastStage(){
        Stage lastStage = null;
        if(progressList!=null){
            for(Map.Entry<String,Stage> item : progressList.entrySet()){
                lastStage = item.getValue();
            }
        }
        return lastStage;
    }

}

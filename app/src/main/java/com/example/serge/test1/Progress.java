package com.example.serge.test1;

import android.content.Context;

import com.example.serge.test1.CustomClasses.CustomLinkedHashMap;
import com.example.serge.test1.Objects.CustomEvents;
import com.example.serge.test1.Objects.Stage;
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
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by sergey37192 on 28.01.2018.
 */

public class Progress {

    public static CustomLinkedHashMap<String, Stage> progressList = null;
    public static Person person = null;


    public static String hell= "hell";



    public static void loadProgress(Context context) throws IOException {
        File file = new File(context.getFilesDir(), "save.dat");
        ObjectInputStream obj = null;
        if(file.exists())
        try {
            obj = new ObjectInputStream( new FileInputStream( file ) );
            progressList = (CustomLinkedHashMap<String, Stage>) obj.readObject();
            person = (Person) obj.readObject();
            obj.close();
        } catch (FileNotFoundException e) {

        } catch (ClassNotFoundException e) {
            obj.close();
        }
        if(progressList== null)
            progressList = new CustomLinkedHashMap<>();
        if(person == null)
            person = new Person();


    }
    public static void saveProgress(Context context){

        try {
            if(progressList!=null){
                FileOutputStream fout = context.openFileOutput("save.dat", Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream( fout);
                out.writeObject( progressList );
                if(person!=null)
                    out.writeObject( person );
                out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static Stage addToProgress(String stage_name){
        if(progressList == null){
            progressList = new CustomLinkedHashMap<>();
        }
        Stage stage = null;
        ArrayList<CustomEvents> EventList = null;
        if((stage = (Stage) Scenario.scenarioList.get( stage_name ))!=null){
            int i = 1;
            while(progressList.containsKey( stage_name ))
                stage_name += i;
            Stage newStage = new Stage(stage_name);
            try {
                EventList = stage.getArray();
                for(CustomEvents e : EventList){
                    CustomEvents currE = (CustomEvents) e.clone();
                    newStage.addToArray( currE );
                    planningScheduleTime( currE );
                }
                progressList.put( stage_name, newStage );
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

}

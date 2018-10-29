package com.example.serge.test1;

import com.example.serge.test1.CustomEvents.IEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Triggers {

    private static HashMap<String, ArrayList<Runnable>> map = null;
    private static int count = 0;

    public static void addTrigger(IEvent event, Runnable runnable){
        String eventName = event.getClass().getName();
        addTrigger(eventName, runnable);
    }

    public static boolean isEmpty(){
        if(count == 0)
            return true;
        else return false;
    }

    public static void addTrigger(String eventName, Runnable runnable){
        if(map == null) {
            map = new HashMap<>();
        }

        ArrayList<Runnable> list = null;
        if((list = map.get(eventName)) == null){
            list = new ArrayList<>();
            map.put(eventName, list);
        }

        list.add(runnable);
        count++;
    }

    public static void removeTrigger(String eventName){
        ArrayList<Runnable> list = map.get(eventName);

        if(list == null)
            return;

        int len = list.size();

        if(len == 0)
            return;

        for(int i = 0; i < len; i++){
            list.remove(i);
            count--;
        }
    }

    public static void removeTrigger(IEvent event){

        if(count == 0)
            return;

        String eventName = event.getClass().getName();
        removeTrigger(eventName);

    }

    public static void removeAllTriggers(IEvent event){
        if(count == 0)
            return;

        String eventName = event.getClass().getName();
        ArrayList<Runnable> list = map.get(eventName);

        if(list == null)
            return;

        for(Runnable runnable : list){
            list.remove(runnable);
            count--;
        }
    }

    public static void removeAll(){
        if(count == 0)
            return;

        map = null;
        count = 0;
    }

    public static ArrayList<Runnable> getTriggers(IEvent event){
        if(count == 0)
            return null;

        String eventName = event.getClass().getName();
        return getTriggers(eventName);
    }

    public static ArrayList<Runnable> getTriggers(String eventName){
        return  map.get(eventName);
    }
}

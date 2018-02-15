package com.example.serge.test1;

/**
 * Created by sergey37192 on 09.01.2018.
 */

public class CustomTimer {
    private static long testtime = 0;
    private static int time = 0;
    private static Boolean fastGame = false;
    private static final int sec = 500;
    private static final int notlong = 300000;
    private static final int slong = 1200000;
    private static final int longer = 3600000;
    private static final int verylong = 7200000;
    private static final int veryveryLong = 21600000;

    public static void addTime(){
        time += sec;
    }

    public static void addTime(String s){
        if(!fastGame){
            switch(s){
                case "notLong": time += notlong; break;
                case "sLong": time += slong; break;
                case "Longer": time += longer; break;
                case "veryLong": time += verylong; break;
                case "veryveryLong": time += veryveryLong; break;
                default: time += sec; break;
            }
        }
        else time += sec;
    }

    public static int getValue(){
        return time;
    }

    public static long getTestValue(){
        if(testtime == 0)
            testtime =  System.currentTimeMillis();
        return testtime;
    }

    public static void clearTimer(){
        time = 0;
    }

    public static void setFastGame(){
        fastGame = true;
    }

    public static void unsetFastGame(){
        fastGame = false;
    }
}

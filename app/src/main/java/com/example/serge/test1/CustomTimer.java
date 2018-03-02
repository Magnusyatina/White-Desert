package com.example.serge.test1;

/**
 * Created by sergey37192 on 09.01.2018.
 */

public class CustomTimer {
    private static long testtime = 0;
    private static long testSec = 1500;
    private static int time = 0;
    private static Boolean fastGame = false;
    private static final long sec = 2500;

    public static long getTestValue(){
        if(testtime == 0)
            testtime = System.currentTimeMillis();
        else
            testtime += sec;
        return testtime;
    }

    public static void addTestTime(int t){
        testtime += t;
    }


    public static void clearTimer(){
        testtime = 0;
    }

    public static void setFastGame(){
        fastGame = true;
    }

    public static void unsetFastGame(){
        fastGame = false;
    }
}

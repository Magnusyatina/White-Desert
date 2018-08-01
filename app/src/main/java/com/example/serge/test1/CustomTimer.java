package com.example.serge.test1;

import java.util.Random;

/**
 * Created by sergey37192 on 09.01.2018.
 */

public class CustomTimer {
    private static long testtime = 0;
    private static Random rand = null;
    private static int min = 1000;
    private static int max = 2500;
    private static boolean fast_game = false;

    public static long getValue(){
        if(testtime == 0)
            testtime = System.currentTimeMillis();
        else
            testtime = testtime + getRand();
        return testtime;
    }

    //Получение случайного числа в диапозоне
    private static long getRand(){
        if(rand == null)
            rand = new Random();

        int dif = max - min;
        return rand.nextInt( dif )+min;
    }

    //Добавление к текущему значению (время) определенное значение
    public static void addTestTime(int t){
        if(!fast_game)
            testtime += t;
        else testtime = testtime + getRand();
    }


    public static void clearTimer(){
        testtime = 0;
    }

    public static void set_mode(boolean b){
        fast_game = b;
    }

}

package org.magnusario.whitedesert;

import java.util.Random;

public class EventTimer {
    private static long currentTime = 0;
    private static Random rand = null;
    private static int min = 1000;
    private static int max = 2500;
    private static boolean fast_game = false;

    public static long nextTime() {
        if (currentTime == 0)
            currentTime = System.currentTimeMillis();
        else
            currentTime = currentTime + getRand();
        return currentTime;
    }

    //Получение случайного числа в диапозоне
    private static long getRand() {
        if (rand == null)
            rand = new Random();

        int dif = max - min;
        return rand.nextInt(dif) + min;
    }

    //Добавление к текущему значению (время) определенное значение
    public static void addTime(int t) {
        if (!fast_game)
            currentTime += t;
        else currentTime = currentTime + getRand();
    }


    public static void clearTimer() {
        currentTime = 0;
    }

    public static void set_mode(boolean b) {
        fast_game = b;
    }

}

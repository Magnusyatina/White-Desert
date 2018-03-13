package com.example.serge.test1.Person;

import java.io.Serializable;

/**
 * Created by sergey37192 on 06.03.2018.
 */

public class Person implements Serializable {
    private boolean matches = true;
    private boolean map = true;
    private boolean flashlight = true;
    private boolean signal_pistol = false;

    public boolean checkItem(int i){
        switch(i){
            case 1: return matches;
            case 2: return map;
            case 3: return flashlight;
            case 4: return signal_pistol;
            default: return false;
        }
    }

    public void setItem(int i){
        switch(i){
            case 1: matches = true; break;
            case 2: map = true; break;
            case 3: flashlight = true; break;
            case 4: signal_pistol = true; break;
            default: break;
        }
    }

    public void unsetItem(int i){
        switch(i){
            case 1: matches = false; break;
            case 2: map = false; break;
            case 3: flashlight = false; break;
            case 4: signal_pistol = false; break;
            default: break;
        }
    }
}

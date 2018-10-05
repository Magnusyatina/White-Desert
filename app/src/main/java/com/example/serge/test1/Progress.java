package com.example.serge.test1;

import com.example.serge.test1.CustomEvents.Event;
import com.example.serge.test1.Person.Person;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sergey37192 on 29.03.2018.
 */

public class Progress implements Serializable {
    private ArrayList<Event> progressList = new ArrayList<>();
    private ArrayList<String> stageList = new ArrayList<>();
    private Person person = new Person();


    public ArrayList<Event> getProgressList() {
        return progressList;
    }

    public ArrayList<String> getStageList() {
        return stageList;
    }

    public Person getPerson() {
        return person;
    }

}

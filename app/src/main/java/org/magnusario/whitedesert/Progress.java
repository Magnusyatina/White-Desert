package org.magnusario.whitedesert;

import org.magnusario.whitedesert.engine.event.Event;

import java.io.Serializable;
import java.util.ArrayList;

public class Progress implements Serializable {
    private static final long serialVersionUID = -6624995693816937755L;
    private ArrayList<Event> progressList = new ArrayList<>();
    private ArrayList<String> stageList = new ArrayList<>();

    public ArrayList<Event> getProgressList() {
        return progressList;
    }

    public ArrayList<String> getStageList() {
        return stageList;
    }

}
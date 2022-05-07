package org.magnusario.whitedesert.event;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sergey37192 on 05.03.2018.
 */

public class Stage implements Serializable, Cloneable {
    private static final long serialVersionUID = -1115959180441557083L;
    private ArrayList<Event> array = new ArrayList<>();
    private String stage_name = null;
    private boolean reentrant = false;

    public Stage(String stage_name) {
        this.stage_name = stage_name;
    }

    public void setArray(ArrayList<Event> array) {
        this.array = array;
    }

    public void addToArray(Event e) {
        array.add(e);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ArrayList<Event> getArray() {
        return array;
    }

    public void setStage_name(String stage_name) {
        this.stage_name = stage_name;
    }

    public String getStage_name() {
        return stage_name;
    }

    public void setReentrant(boolean torf) {
        reentrant = torf;
    }

    public boolean getReentrant() {
        return reentrant;
    }
}

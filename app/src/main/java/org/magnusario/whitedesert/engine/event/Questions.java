package org.magnusario.whitedesert.engine.event;

import java.util.ArrayList;

public class Questions extends Event {
    private static final long serialVersionUID = 7664983681921754500L;
    private ArrayList<Question> list;

    public void put(Question q) {
        if (q != null) {
            if (list == null)
                list = new ArrayList<>();
            list.add(q);
        }
    }

    public ArrayList<Question> getList() {
        return list;
    }
}
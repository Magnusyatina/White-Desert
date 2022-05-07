package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

import java.util.ArrayList;

/**
 * Created by sergey37192 on 18.02.2018.
 */

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

    public void start(EventObserver eventObserver) {
        if (!added) {
            long time = getTimer();
            eventObserver.onEvent(this);

        }

    }

    public ArrayList<Question> getList() {
        return list;
    }
}

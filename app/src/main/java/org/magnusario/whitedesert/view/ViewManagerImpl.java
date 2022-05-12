package org.magnusario.whitedesert.view;

import android.app.Activity;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewManagerImpl implements ViewManager {

    private Map<Integer, View> cachedView = new HashMap<>();

    @Inject
    public Activity activity;

    @Inject
    public ViewManagerImpl() {
    }

    @Override
    public View findViewById(int id) {
        View view = cachedView.get(id);
        if (view == null) {
            view = activity.findViewById(id);
            if (view != null)
                cachedView.put(id, view);
        }
        return view;
    }
}

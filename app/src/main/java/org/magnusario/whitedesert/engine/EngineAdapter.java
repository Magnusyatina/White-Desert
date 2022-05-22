package org.magnusario.whitedesert.engine;

public class EngineAdapter implements Engine {

    @Override
    public void onCreate() {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void start() {
        onCreate();
    }

}

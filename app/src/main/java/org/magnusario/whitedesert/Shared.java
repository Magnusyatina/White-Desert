package org.magnusario.whitedesert;

import android.app.Activity;
import android.content.Context;

import org.magnusario.whitedesert.engine.Engine;
import org.magnusario.whitedesert.engine.EventPoolImpl;

import java.util.Properties;

public class Shared {
    public static EventPoolImpl eventPool;
    public static Engine engine;
    public static Context context;
    public static Activity activity;
    public static Properties properties;
}

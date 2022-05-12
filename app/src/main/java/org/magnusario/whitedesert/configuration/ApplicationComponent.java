package org.magnusario.whitedesert.configuration;

import android.app.Activity;
import android.content.Context;

import org.magnusario.whitedesert.engine.Engine;
import org.magnusario.whitedesert.activity.MainActivity;

import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {ApplicationModule.class, BindsApplicationModule.class, BindsListenerModule.class})
@Singleton
public interface ApplicationComponent {

    Engine engine();

    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Context context);

        @BindsInstance
        Builder mainActivity(Activity activity);

        ApplicationComponent build();
    }
}

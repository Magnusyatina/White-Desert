package org.magnusario.whitedesert.configuration;

import org.magnusario.whitedesert.engine.Engine;
import org.magnusario.whitedesert.engine.EngineImpl;
import org.magnusario.whitedesert.engine.EventPool;
import org.magnusario.whitedesert.engine.EventPoolImpl;
import org.magnusario.whitedesert.engine.publisher.EventPublisher;
import org.magnusario.whitedesert.engine.publisher.EventPublisherImpl;
import org.magnusario.whitedesert.view.ViewManager;
import org.magnusario.whitedesert.view.ViewManagerImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BindsApplicationModule {

    @Binds
    public abstract EventPool bindEventPool(EventPoolImpl eventPool);

    @Binds
    public abstract EventPublisher bindEventPublisher(EventPublisherImpl eventPool);

    @Binds
    public abstract Engine bindEngine(EngineImpl engine);

    @Binds
    public abstract ViewManager viewManager(ViewManagerImpl viewManager);


}

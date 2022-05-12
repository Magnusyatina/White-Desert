package org.magnusario.whitedesert.configuration;

import org.magnusario.whitedesert.engine.listener.EventListener;
import org.magnusario.whitedesert.engine.listener.HintListener;
import org.magnusario.whitedesert.engine.listener.PlayerAnswerListener;
import org.magnusario.whitedesert.engine.listener.QuestionsListener;
import org.magnusario.whitedesert.engine.listener.StageJumpListener;
import org.magnusario.whitedesert.engine.listener.StartGameListener;
import org.magnusario.whitedesert.engine.listener.StartNewGameListener;
import org.magnusario.whitedesert.engine.listener.TextMessageListener;
import org.magnusario.whitedesert.engine.listener.WaitingListener;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

@Module
public abstract class BindsListenerModule {

    @Binds
    @IntoSet
    public abstract EventListener<?> bindTextMessageEventListener(TextMessageListener textMessageListener);

    @Binds
    @IntoSet
    public abstract EventListener<?> bindStartGameEventListener(StartGameListener startGameListener);

    @Binds
    @IntoSet
    public abstract EventListener<?> bindQuestionsEventListener(QuestionsListener questionsListener);

    @Binds
    @IntoSet
    public abstract EventListener<?> bindStartNewGameListener(StartNewGameListener startNewGameListener);

    @Binds
    @IntoSet
    public abstract EventListener<?> bindPlayerAnswerListener(PlayerAnswerListener playerAnswer);

    @Binds
    @IntoSet
    public abstract EventListener<?> bindWaitingListener(WaitingListener waitingListener);

    @Binds
    @IntoSet
    public abstract EventListener<?> bindStageJumpListener(StageJumpListener stageJumpListener);

    @Binds
    @IntoSet
    public abstract EventListener<?> bindHintListener(HintListener hintListener);
}

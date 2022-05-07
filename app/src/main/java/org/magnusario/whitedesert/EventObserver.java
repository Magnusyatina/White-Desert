package org.magnusario.whitedesert;

import org.magnusario.whitedesert.event.AddItem;
import org.magnusario.whitedesert.event.CustomMusic;
import org.magnusario.whitedesert.event.Die;
import org.magnusario.whitedesert.event.Event;
import org.magnusario.whitedesert.event.Hint;
import org.magnusario.whitedesert.event.ImportantMessage;
import org.magnusario.whitedesert.event.PlayerAnswer;
import org.magnusario.whitedesert.event.Questions;
import org.magnusario.whitedesert.event.RandomEvent;
import org.magnusario.whitedesert.event.RemoveItem;
import org.magnusario.whitedesert.event.SetGameMode;
import org.magnusario.whitedesert.event.SetMusic;
import org.magnusario.whitedesert.event.StageJump;
import org.magnusario.whitedesert.event.StartGame;
import org.magnusario.whitedesert.event.StartNewGame;
import org.magnusario.whitedesert.event.TacticalEvent;
import org.magnusario.whitedesert.event.TextMessage;
import org.magnusario.whitedesert.event.Waiting;

/**
 * Created by sergey37192 on 01.04.2018.
 */

public interface EventObserver {
    void onEvent(Event customEvents);

    void onEvent(AddItem addItem);

    void onEvent(Die die);

    void onEvent(ImportantMessage importantMessage);

    void onEvent(PlayerAnswer playerAnswer);

    void onEvent(Questions questions);

    void onEvent(RemoveItem removeItem);

    void onEvent(RandomEvent randomEvent);

    void onEvent(TextMessage textMessage);

    void onEvent(Waiting waiting);

    void onEvent(StartGame startGame);

    void onEvent(StartNewGame startNewGame);

    void onEvent(CustomMusic music);

    void onEvent(SetMusic setMusic);

    void onEvent(SetGameMode gameMode);

    void onEvent(StageJump stageJump);

    void onEvent(TacticalEvent tacticalEvent);

    void onEvent(Hint hint);

    void onCreate();

    void onPause();

    void onResume();

    void start();

}

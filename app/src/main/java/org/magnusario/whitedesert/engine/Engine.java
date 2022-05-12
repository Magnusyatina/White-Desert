package org.magnusario.whitedesert.engine;

import org.magnusario.whitedesert.engine.event.AddItem;
import org.magnusario.whitedesert.engine.event.CustomMusic;
import org.magnusario.whitedesert.engine.event.Die;
import org.magnusario.whitedesert.engine.event.Event;
import org.magnusario.whitedesert.engine.event.Hint;
import org.magnusario.whitedesert.engine.event.ImportantMessage;
import org.magnusario.whitedesert.engine.event.PlayerAnswer;
import org.magnusario.whitedesert.engine.event.Questions;
import org.magnusario.whitedesert.engine.event.RandomEvent;
import org.magnusario.whitedesert.engine.event.RemoveItem;
import org.magnusario.whitedesert.engine.event.SetGameMode;
import org.magnusario.whitedesert.engine.event.SetMusic;
import org.magnusario.whitedesert.engine.event.StageJump;
import org.magnusario.whitedesert.engine.event.StartGame;
import org.magnusario.whitedesert.engine.event.StartNewGame;
import org.magnusario.whitedesert.engine.event.TacticalEvent;
import org.magnusario.whitedesert.engine.event.TextMessage;
import org.magnusario.whitedesert.engine.event.Waiting;

/**
 * Created by sergey37192 on 01.04.2018.
 */

public interface Engine {
    void onEvent(Event customEvents);

    void onEvent(AddItem addItem);

    void onEvent(Die die);

    void onEvent(ImportantMessage importantMessage);

    void onEvent(RemoveItem removeItem);

    void onEvent(RandomEvent randomEvent);

    void onEvent(CustomMusic music);

    void onEvent(SetMusic setMusic);

    void onEvent(SetGameMode gameMode);

    void onEvent(TacticalEvent tacticalEvent);

    void onCreate();

    void onPause();

    void onResume();

    void start();

}

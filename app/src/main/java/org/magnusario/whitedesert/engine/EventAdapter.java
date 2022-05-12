package org.magnusario.whitedesert.engine;

import org.magnusario.whitedesert.Shared;
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
 * Created by sergey37192 on 04.04.2018.
 */

public class EventAdapter implements Engine {
    @Override
    public void onEvent(Event customEvents) {

    }

    @Override
    public void onEvent(AddItem addItem) {

    }

    @Override
    public void onEvent(Die die) {

    }

    @Override
    public void onEvent(ImportantMessage importantMessage) {

    }

    @Override
    public void onEvent(TacticalEvent tacticalEvent) {

    }

    @Override
    public void onEvent(SetGameMode gameMode) {

    }

    @Override
    public void onEvent(RemoveItem removeItem) {

    }

    @Override
    public void onEvent(RandomEvent randomEvent) {

    }

    @Override
    public void onEvent(CustomMusic music) {

    }

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

    @Override
    public void onEvent(SetMusic setMusic) {

    }
}

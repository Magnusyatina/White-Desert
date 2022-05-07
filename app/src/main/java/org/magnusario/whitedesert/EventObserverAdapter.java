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
 * Created by sergey37192 on 04.04.2018.
 */

public class EventObserverAdapter implements EventObserver {
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
    public void onEvent(Hint hint) {

    }

    @Override
    public void onEvent(StageJump stageJump) {

    }

    @Override
    public void onEvent(SetGameMode gameMode) {

    }

    @Override
    public void onEvent(PlayerAnswer playerAnswer) {

    }

    @Override
    public void onEvent(Questions questions) {

    }

    @Override
    public void onEvent(RemoveItem removeItem) {

    }

    @Override
    public void onEvent(RandomEvent randomEvent) {

    }

    @Override
    public void onEvent(TextMessage textMessage) {

    }

    @Override
    public void onEvent(Waiting waiting) {

    }

    @Override
    public void onEvent(StartGame startGame) {

    }

    @Override
    public void onEvent(StartNewGame startNewGame) {

    }

    @Override
    public void onEvent(CustomMusic music) {

    }

    @Override
    public void onCreate() {
        Shared.eventPool.onBind(this);
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

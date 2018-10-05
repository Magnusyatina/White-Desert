package com.example.serge.test1;

import com.example.serge.test1.CustomEvents.AddItem;
import com.example.serge.test1.CustomEvents.Event;
import com.example.serge.test1.CustomEvents.CustomMusic;
import com.example.serge.test1.CustomEvents.Die;
import com.example.serge.test1.CustomEvents.ImportantMessage;
import com.example.serge.test1.CustomEvents.PlayerAnwser;
import com.example.serge.test1.CustomEvents.Questions;
import com.example.serge.test1.CustomEvents.RandomEvent;
import com.example.serge.test1.CustomEvents.RemoveItem;
import com.example.serge.test1.CustomEvents.SetGameMode;
import com.example.serge.test1.CustomEvents.SetMusic;
import com.example.serge.test1.CustomEvents.StageJump;
import com.example.serge.test1.CustomEvents.StartGame;
import com.example.serge.test1.CustomEvents.StartNewGame;
import com.example.serge.test1.CustomEvents.TacticalEvent;
import com.example.serge.test1.CustomEvents.TextMessage;
import com.example.serge.test1.CustomEvents.Waiting;

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
    public void onEvent(StageJump stageJump) {

    }

    @Override
    public void onEvent(SetGameMode gameMode) {

    }

    @Override
    public void onEvent(PlayerAnwser playerAnwser) {

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
        Shared.eventPool.onBind( this );
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

package com.example.serge.test1;

import com.example.serge.test1.CustomEvents.AddItem;
import com.example.serge.test1.CustomEvents.Event;
import com.example.serge.test1.CustomEvents.CustomMusic;
import com.example.serge.test1.CustomEvents.Die;
import com.example.serge.test1.CustomEvents.Hint;
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
 * Created by sergey37192 on 01.04.2018.
 */

public interface EventObserver {
    public void onEvent(Event customEvents);

    public void onEvent(AddItem addItem);

    public void onEvent(Die die);

    public void onEvent(ImportantMessage importantMessage);

    public void onEvent(PlayerAnwser playerAnwser);

    public void onEvent(Questions questions);

    public void onEvent(RemoveItem removeItem);

    public void onEvent(RandomEvent randomEvent);

    public void onEvent(TextMessage textMessage);

    public void onEvent(Waiting waiting);

    public void onEvent(StartGame startGame);

    public void onEvent(StartNewGame startNewGame);

    public void onEvent(CustomMusic music);

    public void onEvent(SetMusic setMusic);

    public void onEvent(SetGameMode gameMode);

    public void onEvent(StageJump stageJump);

    public void onEvent(TacticalEvent tacticalEvent);

    public void onEvent(Hint hint);

    public void onCreate();

    public void onPause();

    public void onResume();

    public void start();

}

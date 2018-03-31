package com.example.serge.test1;

import com.example.serge.test1.Objects.AddItem;
import com.example.serge.test1.Objects.Die;
import com.example.serge.test1.Objects.ImportantMessage;
import com.example.serge.test1.Objects.PlayerAnwser;
import com.example.serge.test1.Objects.Questions;
import com.example.serge.test1.Objects.RemoveItem;
import com.example.serge.test1.Objects.TextMessage;
import com.example.serge.test1.Objects.Waiting;

/**
 * Created by sergey37192 on 01.04.2018.
 */

public interface Engine {
    public void onEvent(AddItem addItem);

    public void onEvent(Die die);

    public void onEvent(ImportantMessage importantMessage);

    public void onEvent(PlayerAnwser playerAnwser);

    public void onEvent(Questions questions);

    public void onEvent(RemoveItem removeItem);

    public void onEvent(TextMessage textMessage);

    public void onEvent(Waiting waiting);

}

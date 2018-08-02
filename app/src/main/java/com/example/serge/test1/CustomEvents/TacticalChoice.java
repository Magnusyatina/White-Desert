package com.example.serge.test1.CustomEvents;

import java.io.Serializable;

public class TacticalChoice implements Serializable{
    private String text = null;
    private String target_stage = null;
    private int chance = 0;

    public TacticalChoice setText(String text){
        this.text = text;
        return this;
    }

    public TacticalChoice setTarget(String target_stage){
        this.target_stage = target_stage;
        return this;
    }

    public TacticalChoice setChance(int chance){
        this.chance = chance;
        return this;
    }
}

package com.example.serge.test1.CustomEvents;

import java.io.Serializable;
import java.util.TreeMap;

public class TacticalChildNode implements Serializable{
    private String text = null;
    private String id = null;
    private TreeMap<String, String> choices = null;
    private String target_stage = null;
    private int chance = 0;

    public TacticalChildNode setText(String text){
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }

    public TacticalChildNode setId(String id){
        this.id = id;
        return this;
    }

    public TacticalChildNode setTarget(String target_stage){
        this.target_stage = target_stage;
        return this;
    }

    public void addChoice(String text, String target){
        if(choices == null)
            choices = new TreeMap<>(  );
        choices.put( text, target );
    }

    public TreeMap<String, String> getChoices() {
        return choices;
    }

    public String getId() {
        return id;
    }
}

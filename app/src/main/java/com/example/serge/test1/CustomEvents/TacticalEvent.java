package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

import java.util.ArrayList;
import java.util.TreeMap;

public class TacticalEvent extends CustomEvents {

    private TreeMap<String, TacticalChildNode> tactical_choices = null;

    private TacticalChildNode currNode = null;

    private String link = null;

    public TacticalEvent (){
        tactical_choices = new TreeMap<>(  );
    }

    public void add(TacticalChildNode choice){
        if(link == null)
            return;

        String choice_id = choice.getId();
        if(choice_id == null)
            return;

        tactical_choices.put( choice_id, choice );

        if(choice_id.equals( link ))
            currNode = choice;
    }

    public void addLink(String link){
        this.link = link;
    }

    public TacticalChildNode getCurrNode() {
        return currNode;
    }

    public void setCurrNode(String id){
        TacticalChildNode tCN = tactical_choices.get(id);
        currNode = tCN;
    }

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent( this );
    }
}

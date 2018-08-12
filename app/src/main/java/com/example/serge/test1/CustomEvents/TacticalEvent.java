package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

import java.util.ArrayList;

public class TacticalEvent extends CustomEvents {

    private ArrayList<TacticalChildNode> tactical_choices = null;

    private TacticalChildNode currNode = null;

    private String link = null;

    public TacticalEvent (){
        tactical_choices = new ArrayList<>( );
    }

    public void add(TacticalChildNode choice){
        if(link == null)
            return;
        tactical_choices.add( choice );
        String choice_id = choice.getId();
        if(choice_id == null)
            return;
        if(choice_id.equals( link ))
            currNode = choice;
    }

    public void addLink(String link){
        this.link = link;
    }

    public TacticalChildNode getCurrNode() {
        return currNode;
    }

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent( this );
    }
}

package com.example.serge.test1.CustomEvents;

import com.example.serge.test1.EventObserver;

import java.util.ArrayList;

public class TacticalEvent extends CustomEvents {

    private ArrayList<TacticalChoice> tactical_choices = null;

    public TacticalEvent (){
        tactical_choices = new ArrayList<>( );
    }

    public void add_choice(TacticalChoice choice){
        tactical_choices.add( choice );
    }

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent( this );
    }
}

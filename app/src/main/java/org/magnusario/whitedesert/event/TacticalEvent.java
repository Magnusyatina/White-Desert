package org.magnusario.whitedesert.event;

import org.magnusario.whitedesert.EventObserver;

import java.util.TreeMap;

public class TacticalEvent extends Event {

    private static final long serialVersionUID = -2558282944668624021L;
    private TreeMap<String, TacticalChildNode> tactical_choices = null;

    private TacticalChildNode currNode = null;

    private String link = null;

    public TacticalEvent() {
        tactical_choices = new TreeMap<>();
    }

    public void add(TacticalChildNode choice) {
        if (link == null)
            return;

        String choice_id = choice.getId();
        if (choice_id == null)
            return;

        tactical_choices.put(choice_id, choice);

        if (choice_id.equals(link))
            currNode = choice;
    }

    public void addLink(String link) {
        this.link = link;
    }

    public TacticalChildNode getCurrNode() {
        return currNode;
    }

    public void setCurrNode(String id) {
        TacticalChildNode tCN = tactical_choices.get(id);
        currNode = tCN;
    }

    @Override
    public void start(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }
}

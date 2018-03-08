package com.example.serge.test1.Conditions;

import com.example.serge.test1.Person.Person;

import java.util.ArrayList;

/**
 * Created by sergey37192 on 08.03.2018.
 */

public class OrOperation implements SyntaxNode{
    private ArrayList<SyntaxNode> children = new ArrayList<SyntaxNode>();

    @Override
    public void add(SyntaxNode child) {
        children.add(child);

    }

    @Override
    public boolean check(Person person) {
        if(children.size()<=0)
            return false;

        for(SyntaxNode child : children) {
            //Если хотя бы один вернет true, то метод вернет true
            if(child.check(person))
                return true;
        }

        return false;

    }
}

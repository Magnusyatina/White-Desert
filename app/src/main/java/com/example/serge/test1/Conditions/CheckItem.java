package com.example.serge.test1.Conditions;

import com.example.serge.test1.Person.Person;

/**
 * Created by sergey37192 on 08.03.2018.
 */

public class CheckItem implements SyntaxNode {
    private int idItem = 0;

    @Override
    public void add(SyntaxNode child) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean check(Person person) {

        return person.checkItem(idItem);
    }


    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
}

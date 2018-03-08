package com.example.serge.test1.Conditions;

import com.example.serge.test1.Person.Person;

/**
 * Created by sergey37192 on 08.03.2018.
 */

public interface SyntaxNode {
    public void add(SyntaxNode child);
    public boolean check(Person person);
}

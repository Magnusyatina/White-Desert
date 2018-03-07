package com.example.serge.test1.CustomClasses;

import java.util.LinkedHashMap;

/**
 * Created by sergey37192 on 07.03.2018.
 */

public class CustomLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    V tail = null;

    public V put(K key, V value){
        tail = value;
        return super.put(key, value);
    }

    public V getTail(){
        return tail;
    }
}

package org.example.repository;

import java.util.HashMap;
import java.util.Map;

public class Repository<K,V> {

    Map<K,V> repository = new HashMap<>();


    public void add(K key, V value){
        repository.put(key,value);
    }

    public V remove(K key){
        return repository.remove(key);
    }

    public V get(K key){
        return repository.get(key);
    }



}

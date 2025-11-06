package org.example.repository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Repository<K,V> {

     Map<K,V> repository = new HashMap<>();

     public Repository(){

     }

     public Repository(Map<K,V> map){
         this.repository = map;
     }

    public void add(K key, V value){
        repository.put(key,value);
    }

    public V remove(K key){
        return repository.remove(key);
    }

    public V get(K key){
        return repository.get(key);
    }

    public List<V> toList(){
         return repository.values().stream().toList();
    }

    public Set<V> getFilteredSet(Predicate<V> predicate){
        return repository.values().stream().filter(predicate).collect(Collectors.toSet());
    }

    public List<V> getSortedCollection(Comparator<V> comparator){
        return repository.values().stream().sorted(comparator).collect(Collectors.toList());
    }






}

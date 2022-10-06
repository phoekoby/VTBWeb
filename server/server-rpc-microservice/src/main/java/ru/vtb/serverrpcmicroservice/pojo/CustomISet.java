package ru.vtb.serverrpcmicroservice.pojo;

import com.hazelcast.map.IMap;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class CustomISet<T> {
    private final IMap<T, T> map;
    private final int defaultTtl;
    private final TimeUnit defaultTimeUnit;

    public boolean add(T value){
        return !Objects.equals(map.put(value, value, defaultTtl, defaultTimeUnit), value);
    }

    public boolean add(T value, int ttl, TimeUnit timeUnit){
        return !Objects.equals(map.put(value, value, ttl, timeUnit), value);
    }

    public boolean contains(T value){
        return map.containsKey(value);
    }

    public void delete(T value){
        map.delete(value);
    }

    public boolean remove(T value){
        return map.remove(value) == value;
    }

    public void clear(){
        map.clear();
    }


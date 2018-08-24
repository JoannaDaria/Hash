package com.github.joannadarka.hash.hashset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HashSet<T> {

    private static final int INCREASE_SIZE_RATIO = 2;
    private static final int MAX_SIZE_TO_BUCKETS_COUNT_RATIO = 2;
    private static int INITIAL_SIZE = 16;

    private Object[] table = new Object[INITIAL_SIZE];
    private int currentSize = 0;

    public void add(T t) {
        if (currentSize >= table.length * MAX_SIZE_TO_BUCKETS_COUNT_RATIO) {
            rearrange();

        }

        int hash = t.hashCode();                    //zwraca hashcode i przypisuje do zm hash
        int bucket = Math.abs(hash % table.length); //liczymy do którego kubełka wpadnie obiekt t

        if (table[bucket] == null) {
            table[bucket] = new ArrayList<T>();     // jeżeli nie ma kubełka, to go zrób
        }

        List<T> bucketList = (List<T>) table[bucket];  //bucketList to lista kubełków (doprecyzowujemy, że jest Listą)
        for (T t1 : bucketList) {                       //to jest to samo co contains()
            if (t1.equals(t)) {
                return;
            }
        }
        bucketList.add(t);                              //wrzucamy obiekt t do odpowiedniego kubełka (gdzie indeks = bucket)
        currentSize ++;
    }


    public boolean contains(T t) {
        int hash = t.hashCode();
        int bucket = Math.abs(hash % table.length);
        if (table[bucket] == null) {
            return false;
        }
        List<T> bucketList = (List<T>) table[bucket];
        for (T t1 : bucketList) {
            if (t1.equals(t)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Arrays.stream(table)
                .filter(Objects::nonNull)
                .flatMap(o -> ((List<T>) o).stream())
                .map(T::toString)
                .collect(Collectors.joining(","));
        sb.append("]");
        return sb.toString();
    }

    private void flatMap() {
    }

    private void rearrange() {
        List<T> elements = new ArrayList<>(currentSize);
        for (Object o : table) {
            if (o != null) {
                List<T> bucket = (List<T>) o;
                elements.addAll(bucket);
            }
        }
        this.table = new Object[this.table.length * INCREASE_SIZE_RATIO];
        this.currentSize = 0;
        elements.forEach(this::add);
    }
}

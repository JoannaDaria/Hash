package com.github.joannadarka.hash;

import com.github.joannadarka.hash.hashset.HashSet;

import java.util.Optional;
import java.util.UUID;

public class Application {
    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            hashSet.add(UUID.randomUUID().toString());
        }
        System.out.println(hashSet);


        Optional<String> o1 = Optional.of("ABC");
        String str = o1.filter(s -> s.contains("A"))
                .map(String::toLowerCase)
                .orElse("Default String");
        System.out.println(str);
    }
}
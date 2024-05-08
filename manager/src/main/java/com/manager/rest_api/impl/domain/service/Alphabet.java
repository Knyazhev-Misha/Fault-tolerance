package com.manager.rest_api.impl.domain.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Alphabet {
    public static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static String number = "0123456789";
    
    public static List<String> toList(String... inputs) {
             
        List<String> resultList = new ArrayList<>();
        for (String input : inputs) {
            for (char c : input.toCharArray()) {
                resultList.add(String.valueOf(c));
            }
        }
        return resultList;
    }

    @SafeVarargs
    public static List<String> uniqConcatList(List<String>... inputs) {

        
        List<String> resultList = new ArrayList<>();
        for (List<String> input : inputs) {
            for(String word : input){
                resultList.add(word);
            }
        }

        Set<String> set = new LinkedHashSet<>(resultList); 
        resultList = new ArrayList<>(set);
        return resultList;
    }
}

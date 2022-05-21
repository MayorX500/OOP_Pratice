package Auxiliar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import Exceptions.*;
import House.*;
import SmartDevice.*;

public class Auxiliar_Methods {
    // function to sort hashmap by values
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        HashMap<String, Double> temp
            = hm.entrySet()
                  .stream()
                  .sorted((i1, i2)
                              -> i2.getValue().compareTo(
                                  i1.getValue()))
                  .collect(Collectors.toMap(
                      Map.Entry::getKey,
                      Map.Entry::getValue,
                      (e1, e2) -> e1, LinkedHashMap::new));
 
        return temp;
    }

    public static Set<Pair<Integer,Double>> time_price(House h) throws Empty_House{
        Set<Pair<Integer,Double>> out = new HashSet<>();
        if(h.getDivisions().size()>0){
            for(Divisions div: h.getDivisions()){
                if(div.getDevices().size()>0){
                    for(SmartDevice device : div.getDevices()){
                        Consumption a = (Consumption)device;
                        Pair<Integer,Double> price = new Pair<Integer,Double>(device.getTime_on(), a.getPower_usage());
                        out.add(price);
                    }
                }
            }
        }else throw new Empty_House(h.getAddress().toString());
        return out;
    }
    public static String reverseName (String name) {

        name = name.trim();
    
        StringBuilder reversedNameBuilder = new StringBuilder();
        StringBuilder subNameBuilder = new StringBuilder();
    
        for (int i = 0; i < name.length(); i++) {
    
            char currentChar = name.charAt(i);
    
            if (currentChar != ' ' && currentChar != '-') {
                subNameBuilder.append(currentChar);
            } else {
                reversedNameBuilder.insert(0, currentChar + subNameBuilder.toString());
                subNameBuilder.setLength(0);
            }
    
        }
    
        return reversedNameBuilder.insert(0, subNameBuilder.toString()).toString();
    
    }
}

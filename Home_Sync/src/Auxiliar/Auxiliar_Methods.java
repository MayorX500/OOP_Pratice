package Auxiliar;

import java.util.HashSet;
import java.util.Set;

import Exceptions.*;
import House.*;
import SmartDevice.*;

public class Auxiliar_Methods {
    public static Set<Pair<Integer,Double>> time_price(House h) throws Empty_House{
        Set<Pair<Integer,Double>> out = new HashSet<>();
        if(h.getDivisions().size()>0){
            for(Divisions div: h.getDivisions()){
                if(div.getDevices().size()>0){
                    for(SmartDevice device : div.getDevices()){
                        Consumption a = (Consumption)device;
                        Pair<Integer,Double> price = new Pair<Integer,Double>(device.getTime_on(), a.getPower_usage());
                        out.add(price.clone());
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

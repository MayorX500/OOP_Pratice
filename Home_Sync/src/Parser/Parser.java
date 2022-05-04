package Parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Locale;

import Client.*;
import Exceptions.*;
import House.*;
import House.Address;
import MVC_House_Sync.*;
import Simulator.*;
import SmartDevice.*;
import Suppliers.*;

import com.github.javafaker.*;

import Auxiliar.MyRandom;
import Auxiliar.Pair;

public class Parser {
    public static Model parse(String fileName) throws  Wrong_Line{
        Set<Suppliers> suppliers = new HashSet<>();
        Set<House> houses = new HashSet<>();
        Model model = new Model();
        List<String> lines = readFile(fileName);
        House lasth = null;
        Divisions lastd = null;
        String[] splitLine;
        for (String line : lines) {
            splitLine = line.split(":", 2);
            switch (splitLine[0]) {
                case "Fornecedor" -> {
                    Suppliers e = new Suppliers(splitLine[1]);
                    suppliers.add(e);
                }
                case "Casa" -> {
                    if(lasth != null && lastd != null){
                        lasth.addDivision(lastd.clone());
                        houses.add(lasth.clone());
                    }
                    String[] args = splitLine[1].split(",");
                    Client client = new Client(args[0],Integer.parseInt(args[1]));
                    Suppliers supplier = new Suppliers();
                    for(Suppliers sup : suppliers){
                        if(sup.getSupplier_name().equals(args[2])){
                            supplier = sup.clone();
                        }
                    }
                    Faker faker = new Faker(new Locale("pt"));
                    Address add = new Address(faker.address().streetName(),
                                            Integer.parseInt(faker.address().buildingNumber()),
                                            faker.address().city(),
                                            new Pair<Integer,Integer>(
                                                        Integer.parseInt(faker.address().zipCode()),
                                                        MyRandom.random_i(0, 999)
                                                        ));
                    House house = new House(add.clone(), client.clone(), new HashSet<>(), supplier.clone(), 0);
                    lasth = house;
                }
                case "Divisao" -> {
                    if(lasth != null && lastd != null){
                        lasth.addDivision(lastd.clone());
                    }
                    Divisions division = new Divisions(splitLine[1], new HashSet<>());
                    lastd = division;                    
                }

                case "SmartBulb" -> {
                    if(lasth == null || lastd == null){
                        throw new Wrong_Line(); // Wrong devices Order
                    }
                    String[] args = splitLine[1].split(",");
                    Faker fake = new Faker();
                    SmartDevice bulb = new SmartBulb(fake.funnyName().name(),args[0],Float.parseFloat(args[1]),Float.parseFloat(args[2]));
                    lastd.addDevice(bulb.clone());
                
                }
                case "SmartSpeaker" -> {
                    if(lasth == null || lastd == null){
                        throw new Wrong_Line(); // Wrong devices Order
                    }
                    String[] args = splitLine[1].split(",");
                    Faker fake = new Faker();
                    SmartDevice speaker = new SmartSpeaker(fake.funnyName().name(),args[2],Integer.parseInt(args[0]),Float.parseFloat(args[3]),args[1]);
                    lastd.addDevice(speaker.clone());
                
                }
                case "SmartCamera" -> {
                    if(lasth == null || lastd == null){
                        throw new Wrong_Line(); // Wrong devices Order
                    }
                    String[] args = splitLine[1].split(",");

                    String resol = args[0].replaceAll("\\(", "").replaceAll("\\)","");
                    String[] resolution = resol.split("x");

                    Faker fake = new Faker();
                    SmartDevice camera = new SmartCamera(fake.funnyName().name(),new Pair<Integer,Integer>(Integer.parseInt(resolution[0]),Integer.parseInt(resolution[1])),Double.parseDouble(args[1]),Float.parseFloat(args[2]));
                    lastd.addDevice(camera.clone());
                
                }
                default -> throw new Wrong_Line();
            }
        }
        if(lasth != null && lastd != null){
            lasth.addDivision(lastd.clone());
            houses.add(lasth.clone());
        }
        model.setSimulator(new Simulator(houses,suppliers));
        return model;
    }

    public static List<String> readFile(String Filename) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(Filename), StandardCharsets.UTF_8); }
        catch(IOException exc) { View.showException(exc); lines = new ArrayList<>(); }
        return lines;
    }
}

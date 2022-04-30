package Parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Exceptions.Wrong_Line;
import House.Divisions;
import House.House;
import MVC_House_Sync.Model;
import Suppliers.Suppliers;

public class Parser {
    public static Model parse(String fileName) throws  Wrong_Line{
        Model model = new Model();
        List<String> lines = readFile(fileName);
        Divisions last = null;
        House j = null;
        String[] splitLine;
        for (String linha : lines) {
            splitLine = linha.split(":", 2);
            switch (splitLine[0]) {
                case "Fornecedor" -> {
                    Suppliers e = new Suppliers(splitLine[1]);
                    model.addSupplier(e);
                    last = e;
                }
                case "Guarda-Redes", "Defesa", "Medio", "Lateral", "Avancado" -> {
                    j = FootballPlayer.parse(splitLine[0], splitLine[1]);
                    model.addPlayer(j);
                    if (last == null)
                        throw new Wrong_Line(); //we need to insert the player into the team
                    last.addPlayer(j); //if no team was parsed previously, file is not well-formed
                }
                case "Jogo" -> {
                    String[] args = splitLine[1].split(",");
                    int scoreT1 = Integer.parseInt(args[2]);
                    int scoreT2 = Integer.parseInt(args[3]);
                    Team t1 = model.getTeamByName(args[0]);
                    Team t2 = model.getTeamByName(args[1]);
                    if (scoreT1 > scoreT2) {
                        t1.setWins(t1.getWins() + 1);
                        t2.setLosses(t2.getLosses() + 1);
                    } else if (scoreT2 > scoreT1) {
                        t2.setWins(t2.getWins() + 1);
                        t1.setLosses(t1.getLosses() + 1);
                    } else {
                        t1.setTies(t1.getTies() + 1);
                        t2.setTies(t2.getTies() + 1);
                    }
                }
                default -> throw new Wrong_Line();
            }
        }
        return model;
    }

    public static List<String> readFile(String Filename) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(Filename), StandardCharsets.UTF_8); }
        catch(IOException exc) { lines = new ArrayList<>(); }
        return lines;
    }


}

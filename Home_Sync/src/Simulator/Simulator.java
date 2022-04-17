package Simulator;

import java.time.LocalDate;
import java.util.HashSet;

import House.*;
import Exceptions.*;
import Client.*;
import Auxiliar.*;
import Suppliers.*;

public class Simulator {
	private HashSet<House> houses;
	private LocalDate simulation_date;
	private HashSet<Events> events;

    public Simulator(HashSet<House> houses, LocalDate simulation_date, HashSet<Events> events) {
        this.setHouses(houses);;
        this.simulation_date = simulation_date;
        this.setEvents(events);;
    }

    public Simulator(HashSet<House> houses) {
        this(houses, LocalDate.now(),new HashSet<Events>());
    }

    public Simulator() {
        this(new HashSet<House>());
    }

    public Simulator(Simulator sim) {
        this(sim.getHouses(),sim.getSimulation_date(),sim.getEvents());
    }

    public HashSet<House> getHouses() {
        HashSet<House> out = new HashSet<>();
        if(this.houses.size()>0){
            for(House house : this.houses){
                out.add(house.clone());
            }
        }
        return out;
    }

    public void setHouses(HashSet<House> houses) {
        HashSet<House> out = new HashSet<>();
        if(houses.size()>0){
            for(House house : houses){
                out.add(house.clone());
            }
        }
        
        this.houses = out;
    }

    public LocalDate getSimulation_date() {
        return this.simulation_date;
    }

    public void setSimulation_date(LocalDate simulation_date) {
        this.simulation_date = simulation_date;
    }

    public HashSet<Events> getEvents() {
        HashSet<Events> out = new HashSet<>();
        if(this.events.size()>0){
            for(Events event : this.events){
                out.add(event.clone());
            }
        }
        return out;
    }

    public void setEvents(HashSet<Events> events) {
        HashSet<Events> out = new HashSet<>();
        if(events.size()>0){
            for(Events event : events){
                out.add(event.clone());
            }
        }
        this.events = out;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Simulator)) {
            return false;
        }
        Simulator simulator = (Simulator) o;
        return this.houses.equals(simulator.getHouses()) && this.simulation_date.equals(simulator.getSimulation_date()) && this.events.equals(simulator.getEvents());
    }

    @Override
    public String toString() {
        return "{" +
            " houses='" + getHouses().toString() + "'" +
            ", simulation_date='" + getSimulation_date().toString() + "'" +
            ", events='" + getEvents().toString() + "'" +
            "}";
    }

    //

    public void increment_Day() throws Empty_Simulation{
        this.setSimulation_date(this.getSimulation_date().plusDays(1));
        if(this.houses.size()>0){
            for(House house : this.houses){
                try{
                    System.out.println(house.getHouse_daily_Price());
                }
                catch(Empty_House empty){
                    System.out.println(empty.toString());
                }
            }

        }
        else throw new Empty_Simulation(this.toString()); 
    }
}

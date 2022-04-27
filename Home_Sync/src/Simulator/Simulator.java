package Simulator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import House.*;

public class Simulator {
	private Set<House> houses;
	private LocalDate simulation_date;
	private Set<Events> events;
    private Set<Invoice> invoices;

    public Simulator(Set<House> houses, LocalDate simulation_date, Set<Events> events) {
        this.setHouses(houses);;
        this.simulation_date = simulation_date;
        this.setEvents(events);;
    }

    public Simulator(Set<House> houses) {
        this.houses = houses;
        this.simulation_date = LocalDate.now();
        this.events = new HashSet<Events>();
        Set<Invoice> invoices = new HashSet<>(); 
        if (houses.size() > 0){
            for (House house : houses){
                Invoice a = new Invoice(house.getDaily_consumption(), house.getHouse_id(), house.getAddress(), simulation_date, simulation_date );
                invoices.add(a);
            }
        }
        this.invoices = invoices;
    }

    public Set<Invoice> getInvoices() {
        return this.invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }



    public Simulator() {
        this(new HashSet<House>());
    }

    public Simulator(Simulator sim) {
        this(sim.getHouses(),sim.getSimulation_date(),sim.getEvents());
    }

    public Set<House> getHouses() {
        Set<House> out = new HashSet<>();
        if(this.houses.size()>0){
            for(House house : this.houses){
                out.add(house.clone());
            }
        }
        return out;
    }

    public void setHouses(Set<House> houses) {
        Set<House> out = new HashSet<>();
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

    public Set<Events> getEvents() {
        Set<Events> out = new HashSet<>();
        if(this.events.size()>0){
            for(Events event : this.events){
                out.add(event.clone());
            }
        }
        return out;
    }

    public void setEvents(Set<Events> events) {
        Set<Events> out = new HashSet<>();
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

    @Override
    public Simulator clone(){
        return new Simulator(this);
    }

 /*   public  void increment_Day() throws Empty_Simulation{
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
    */

    public House getHouseFromAddress(Address address) { ///////////morada sem casa
        House out = new House();
        if (houses.size() > 0){
            for (House house: this.houses){
                if (house.getAddress().equals(address)) out = house.clone();
            }
        } 
        else  out = null;
        return out;
    }

}

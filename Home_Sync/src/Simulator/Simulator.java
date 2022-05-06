package Simulator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import Auxiliar.Consumption;
import Auxiliar.Pair;
import Exceptions.Empty_Division;
import Exceptions.Empty_House;
import Exceptions.Empty_Simulation;
import House.*;
import MVC_House_Sync.View;
import SmartDevice.SmartDevice;
import Suppliers.*;

public class Simulator implements Serializable{
	private Set<House> houses;
	private LocalDateTime starting_simulation_date = LocalDateTime.now();
    private LocalDateTime simulation_date;
	private Set<Events> events;
    private Set<Invoice> invoices;
    private Set<Suppliers> suppliers;

    public Simulator(Set<House> houses, LocalDateTime simulation_date, Set<Events> events,Set<Invoice> invoices,Set<Suppliers> suppliers) {
        this.setHouses(houses);;
        this.simulation_date = simulation_date;
        this.setEvents(events);
        this.setInvoices(invoices);
        this.setSuppliers(suppliers);
    }

    public Simulator(Set<House> houses, Set<Suppliers> suppliers) {
        this.houses = houses;
        this.simulation_date = LocalDateTime.now();
        this.events = new HashSet<Events>();
        this.invoices = new HashSet<>(); 
        this.setSuppliers(suppliers);
    }

    public Set<Invoice> getInvoices() {
        return this.invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<Suppliers> getSuppliers(){
        Set<Suppliers> out = new HashSet<>();
        for(Suppliers s : this.suppliers){
            out.add(s.clone());
        }
        return out;
    }

    public void setSuppliers(Set<Suppliers> suppliers) {
        Set<Suppliers> out = new HashSet<>();
        for(Suppliers s : suppliers){
            out.add(s.clone());
        }
        this.suppliers = out;
    }

    public Simulator() {
        this(new HashSet<House>(),new HashSet<Suppliers>());
    }

    public Simulator(Simulator sim) {
        this(sim.getHouses(),sim.getSimulation_date(),sim.getEvents(),sim.getInvoices(),sim.getSuppliers());
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

    public LocalDateTime getSimulation_date() {
        return this.simulation_date;
    }

    public void setSimulation_date(LocalDateTime simulation_date) {
        this.simulation_date = simulation_date;
    }

    public LocalDateTime getStarting_simulation_date() {
        return this.starting_simulation_date;
    }

    public void setStarting_simulation_date(LocalDateTime s_imulation_date) {
        this.starting_simulation_date = s_imulation_date;
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

    public Set<House> eliminateHouses (Set<House> houses, Address address){
        if(houses.size() > 0){
            for (House house: this.houses){
                if (house.getAddress().equals(address))
                    this.houses.remove(house);
            }
        }
        else return null;
        return houses;
    }

    public Invoice getInvoiceFromAddress(Address address){
        Invoice out = new Invoice();
        if (invoices.size() > 0){
            for (Invoice invoice : this.invoices){
                if (invoice.getAddress().equals(address)) out = invoice.clone();
            }
        } 
        else  out = null;
        return out;
    }

    public void addHouse(House e){
        this.events.add(new Events("House " + e.getHouse_id() + " was added", LocalDateTime.now()));
        this.houses.add(e.clone());
        for(Suppliers sup : this.suppliers){
            if (! sup.getSupplier_name().equals(e.getSupplier().getSupplier_name())){
                this.suppliers.add(e.getSupplier().clone());
            }
        }
    }

    public void advanceOneHour() throws Empty_Simulation, Empty_House, Empty_Division{
        if(this.houses.size()>0){
            for(House house : houses){
                if (house.getDivisions().size()>0){
                    for(Divisions division : house.getDivisions()){
                        if(division.getDevices().size()>0){
                            for(SmartDevice device : division.getDevices()){
                                if(device.is_on())
                                    device.add_time_on(1);
                            }
                        }else throw new Empty_Division(division.getDivision_name());
                    }
                }else throw new Empty_House(house.getHouse_id() + "");
            }
        }
        else throw new Empty_Simulation(this.toString());
    }

    public Set<Pair<Integer,Double>> time_price(House h) throws Empty_Division, Empty_House{
        Set<Pair<Integer,Double>> out = new HashSet<>();
        if(h.getDivisions().size()>0){
            for(Divisions div: h.getDivisions()){
                if(div.getDevices().size()>0){
                    for(SmartDevice device : div.getDevices()){
                        Consumption a = (Consumption)device;
                        Pair<Integer,Double> price = new Pair<Integer,Double>(device.getTime_on(), a.getPower_usage());
                        out.add(price.clone());
                    }
                }else throw new Empty_Division(div.getDivision_name());
            }
        }else throw new Empty_House(h.getAddress().toString());
        return out;
    }

    public double create_invoice_final_usage(House house) throws Empty_House{
        double final_usage = 0;
        try{
            Set<Pair<Integer,Double>> prices = time_price(house);
            if(prices.size()>0){
                for(Pair<Integer,Double> pair : prices){
                    final_usage += pair.getL()*pair.getR();
                }
            }else throw new Empty_House(house.getAddress().toString());
        }
        catch(Empty_House empty_house){
            View.showException(empty_house);
        }
        catch(Empty_Division empty_division){
            View.showException(empty_division);
        }

        return final_usage;
    }
}

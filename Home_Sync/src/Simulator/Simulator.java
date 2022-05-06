package Simulator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


import Exceptions.*;
import House.*;
import SmartDevice.*;
import Suppliers.*;

public class Simulator implements Serializable{
	private Set<House> houses;
	private LocalDateTime starting_simulation_date = LocalDateTime.now();
    private LocalDateTime simulation_date = LocalDateTime.now();
	private Set<Events> events;
    private Set<Invoice> invoices;
    private Set<Suppliers> suppliers;

    public Simulator(Set<House> houses,LocalDateTime starting, LocalDateTime simdate, Set<Events> events,Set<Invoice> invoices,Set<Suppliers> suppliers) {
        this.setHouses(houses);;
        this.setEvents(events);
        this.setInvoices(invoices);
        this.setSuppliers(suppliers);
        this.simulation_date = simdate;
        this.starting_simulation_date = starting;
    }

    public Simulator(Set<House> houses, Set<Events> events,Set<Invoice> invoices,Set<Suppliers> suppliers) {
        this.setHouses(houses);;
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
        this(sim.getHouses(),sim.getStarting_simulation_date(),sim.getSimulation_date(),sim.getEvents(),sim.getInvoices(),sim.getSuppliers());
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

    public void addInvoice(Invoice inv){
        this.invoices.add(inv.clone());
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

    public Invoice create_invoice(House h,LocalDateTime start_date,LocalDateTime final_date){
        Invoice inv = new Invoice();
        try {
            inv = Invoice.generateInvoice(h, start_date, final_date);
        } catch (Empty_House e) {
            inv = new Invoice(h.getDaily_consumption(), h.getHouse_id(), h.getAddress(), start_date, final_date, 0, 0, 0, 0);
        }
        return inv;
    }

    public Set<Invoice> generate_invoices(LocalDateTime start_date,LocalDateTime final_date) throws Empty_Simulation{
        Set<Invoice> s = new HashSet<>();
        if(this.getHouses().size()>0){
            for(House h : this.getHouses()){
                s.add(create_invoice(h, start_date, final_date));
            }
        }else throw new Empty_Simulation("There are no houses in this simulation");
        setInvoices(s);
        return s;
    }
}

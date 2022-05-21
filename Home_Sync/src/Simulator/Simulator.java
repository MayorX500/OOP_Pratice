package Simulator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


import Auxiliar.Auxiliar_Methods;
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
            out.add(s);
        }
        return out;
    }

    public void setSuppliers(Set<Suppliers> suppliers) {
        Set<Suppliers> out = new HashSet<>();
        for(Suppliers s : suppliers){
            out.add(s);
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
                out.add(house);
            }
        }
        return out;
    }

    public void setHouses(Set<House> houses) {
        Set<House> out = new HashSet<>();
        if(houses.size()>0){
            for(House house : houses){
                out.add(house);
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
                out.add(event);
            }
        }
        return out;
    }

    public void setEvents(Set<Events> events) {
        Set<Events> out = new HashSet<>();
        if(events.size()>0){
            for(Events event : events){
                out.add(event);
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
                if (house.getAddress().equals(address)) out = house;
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
                if (invoice.getAddress().equals(address)) out = invoice;
            }
        } 
        else  out = null;
        return out;
    }

    public void addHouse(House e){
        this.events.add(new Events("House " + e.getHouse_id() + " was added", LocalDateTime.now()));
        this.houses.add(e);
        for(Suppliers sup : this.suppliers){
            if (! sup.getSupplier_name().equals(e.getSupplier().getSupplier_name())){
                this.suppliers.add(e.getSupplier());
            }
        }
    }

    public void addSupplier(Suppliers e){
        this.events.add(new Events("Supplier " + e.getSupplier_id() + " was added", LocalDateTime.now()));
        this.suppliers.add(e);
    }

    public void addInvoice(Invoice inv){
        this.invoices.add(inv);
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
                Invoice v = create_invoice(h, start_date, final_date);
                s.add(v);
                this.events.add(new Events("Invoice " + v.getId() + " was created for House "+ h.getHouse_id() + ".", LocalDateTime.now()));
            }
        }else throw new Empty_Simulation("There are no houses in this simulation");
        setInvoices(s);
        return s;
    }

    public Invoice getMostExpensiveInvoice(LocalDateTime start_date,LocalDateTime final_date) throws Empty_Simulation{
        generate_invoices(start_date, final_date);
        double max = 0;
        Invoice i = new Invoice();
        if(this.getInvoices().size()>0){
            for(Invoice invoice : invoices){
                max = Math.max(invoice.getPrice_to_pay(), max);
                if(max == invoice.getPrice_to_pay()){
                    i = invoice;
                }
            }
        }

        return i;
    }
    public Suppliers getRichSupplier() throws Empty_Simulation{
        if(houses.size()>0){
            House h[] = new House[houses.size()];
            this.getHouses().toArray(h);
            Arrays.sort(h);//ordenar por supplier

            int max_count = 1;
            Suppliers res = h[0].getSupplier();
            int curr_count = 1;
     
            for (int i = 1; i < h.length; i++) {
                if (h[i].getSupplier().equals(h[i - 1].getSupplier()))
                    curr_count++;
                else
                    curr_count = 1;
     
                if (curr_count > max_count) {
                    max_count = curr_count;
                    res = h[i - 1].getSupplier();
                }
            }
            return res;
        } else throw new Empty_Simulation("Empty Simulation");
    }

    public Set<Invoice> invoicesFromSupplier (Suppliers supplier,LocalDateTime start_date, LocalDateTime final_date) throws Empty_Simulation{
        Set<Invoice> out = new HashSet<>();
        if(houses.size() > 0){
            for (House h : houses){
                if (h.getSupplier().getSupplier_name().equals(supplier.getSupplier_name())) {
                    Invoice inv = new Invoice();
                    try {
                        inv = Invoice.generateInvoice(h, start_date, final_date);
                        this.events.add(new Events("Invoice " + inv.getId() + " was created for House "+ h.getHouse_id() + ".", LocalDateTime.now()));
                    } catch (Exception e) {
                        inv = new Invoice(h.getDaily_consumption(), h.getHouse_id(), h.getAddress(), start_date, final_date, 0, 0, 0, 0);
                    }
                    out.add(inv);
                    this.invoices.add(inv);
                }
            }
            return out;
        } else throw new Empty_Simulation("Empty Simulation");
    }

    public HashMap<String, Double> topTenConsumers() throws Empty_Simulation{
        if(houses.size() > 0){
            HashMap<String, Double>  arr = new HashMap<>();
            for (House h : houses){
                arr.put(h.getOwner().getClient_name(), h.getDaily_consumption());   
            }
            return Auxiliar_Methods.sortByValue(arr);

        }else throw new Empty_Simulation("Empty Simulation");
    }
}



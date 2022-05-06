package Simulator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import Auxiliar.*;
import House.*;
import Exceptions.*;

public class Invoice implements Serializable{
    private double mensal_consum;
    private int id;
    private Address address;
    private LocalDateTime initial_date;
    private LocalDateTime final_date;
    private double final_usage;
    private double price_to_pay;
    private float price_per_watt;
    private double aproximation;

    public Invoice() {
        this(0, 0, new Address(), LocalDateTime.now(), LocalDateTime.now(),0,0,0,0);
    }

    public Invoice(Invoice o){
        this(o.getMensal_consum(), o.getId(), o.getAddress(), o.getInitial_date(), o.getFinal_date(),o.getFinal_price(),o.getPrice_per_watt(),o.getPrice_to_pay(),o.getAproximation());
    }

    public Invoice(double mensal_consum, int id, Address address, LocalDateTime initial_date, LocalDateTime final_date, double final_usage,float price_per_watt,double price_to_pay,double aproximation){
        this.mensal_consum = mensal_consum;
        this.id = id;
        this.address = address;
        this.initial_date = initial_date;
        this.final_date = final_date;
        this.final_usage = final_usage;
        this.price_per_watt = price_per_watt;
        this.price_to_pay = price_to_pay;
        this.aproximation = aproximation;
    }

    public double getMensal_consum() {
        return this.mensal_consum;
    }

    public void setMensal_consum(double mensal_consum) {
        this.mensal_consum = mensal_consum;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getInitial_date() {
        return this.initial_date;
    }

    public void setInitial_date(LocalDateTime initial_date) {
        this.initial_date = initial_date;
    }

    public LocalDateTime getFinal_date() {
        return this.final_date;
    }

    public void setFinal_date(LocalDateTime final_date) {
        this.final_date = final_date;
    }

    public double getFinal_price(){
        return this.final_usage;
    }

    public void setFinal_price(double final_usage){
        this.final_usage = final_usage;
    }


    public double getPrice_to_pay() {
        return this.price_to_pay;
    }

    public void setPrice_to_pay(double price_to_pay) {
        this.price_to_pay = price_to_pay;
    }

    public double getAproximation() {
        return this.aproximation;
    }

    public void setAproximation(double aproximation) {
        this.aproximation = aproximation;
    }

    public float getPrice_per_watt() {
        return this.price_per_watt;
    }

    public void setPrice_per_watt(float price_per_watt) {
        this.price_per_watt = price_per_watt;
    }

    @Override
    public String toString() {
        return "{" +
            " mensal_consum='" + getMensal_consum() + "'" +
            ", id='" + getId() + "'" +
            ", address='" + getAddress() + "'" +
            ", initial_date='" + getInitial_date() + "'" +
            ", final_date='" + getFinal_date() + "'" +
            ", final_usage='" + getFinal_price() + "'" +
            ", price_to_pay='" + getPrice_to_pay() + "'" +
            ", price_per_watt='" + getPrice_per_watt() + "'" +
            ", aproximation='" + getAproximation() + "'" +
            "}";
    }


    public Invoice clone(){
        return new Invoice(this);
    }

    public static Invoice generateInvoice(House h,LocalDateTime start_date,LocalDateTime final_date) throws Empty_House{
        double final_price_all_devices = 0;
        try{
            Set<Pair<Integer,Double>> prices = Auxiliar_Methods.time_price(h);
            if(prices.size()>0){
                for(Pair<Integer,Double> pair : prices){
                    final_price_all_devices += pair.getL()*pair.getR();
                }
            }else throw new Empty_House(h.getAddress().toString());
        }
        catch(Empty_House empty_house){
        throw empty_house;    
        }

        return new Invoice(h.getDaily_consumption(),
                            h.getHouse_id(),
                            h.getAddress(),
                            start_date,
                            final_date,
                            final_price_all_devices/h.getSupplier().getSupplier_rate(),
                            h.getSupplier().getSupplier_rate(),
                            final_price_all_devices,
                            h.getDaily_consumption()*h.getSupplier().getSupplier_rate());
    }
}

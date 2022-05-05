package Simulator;

import java.io.Serializable;
import java.time.LocalDateTime;

import House.Address;

public class Invoice implements Serializable{
    private double mensal_consum;
    private int id;
    private Address address;
    private LocalDateTime initial_date;
    private LocalDateTime final_date;
    private double final_usage;

    public Invoice() {
        this(0, 0, new Address(), LocalDateTime.now(), LocalDateTime.now(),0);
    }

    public Invoice(Invoice o){
        this(o.getMensal_consum(), o.getId(), o.getAddress(), o.getInitial_date(), o.getFinal_date(),o.getFinal_Price());
    }

    public Invoice(double mensal_consum, int id, Address address, LocalDateTime initial_date, LocalDateTime final_date, double final_usage){
        this.mensal_consum = mensal_consum;
        this.id = id;
        this.address = address;
        this.initial_date = initial_date;
        this.final_date = final_date;
        this.final_usage = final_usage;
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

    public double getFinal_Price(){
        return this.final_usage;
    }

    public void eetFinal_Price(double final_usage){
        this.final_usage = final_usage;
    }

    @Override
    public String toString() {
        return "{" +
            " mensal_consum='" + getMensal_consum() + "'" +
            ", id='" + getId() + "'" +
            ", address='" + getAddress() + "'" +
            ", initial_date='" + getInitial_date() + "'" +
            ", final_date='" + getFinal_date() + "'" +
            ", final_usage='" + getFinal_Price() + "'" +
            "}";
    }

    public Invoice clone(){
        return new Invoice(this);
    }
}

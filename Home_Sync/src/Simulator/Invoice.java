package Simulator;

import java.time.LocalDateTime;

import House.Address;

public class Invoice extends Simulator{
    private double mensal_consum;
    private int id;
    private Address address;
    private LocalDateTime initial_date;
    private LocalDateTime final_date;

    public Invoice() {
        this(0, 0, new Address(), LocalDateTime.now(), LocalDateTime.now());
    }

    public Invoice(Invoice o){
        this(o.getMensal_consum(), o.getId(), o.getAddress(), o.getInitial_date(), o.getFinal_date());
    }

    public Invoice(double mensal_consum, int id, Address address, LocalDateTime initial_date, LocalDateTime final_date){
        this.mensal_consum = mensal_consum;
        this.id = id;
        this.address = address;
        this.initial_date = initial_date;
        this.final_date = final_date;
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

    public Invoice mensal_consum(double mensal_consum) {
        setMensal_consum(mensal_consum);
        return this;
    }

    public Invoice id(int id) {
        setId(id);
        return this;
    }

    public Invoice address(Address address) {
        setAddress(address);
        return this;
    }

    public Invoice initial_date(LocalDateTime initial_date) {
        setInitial_date(initial_date);
        return this;
    }

    public Invoice final_date(LocalDateTime final_date) {
        setFinal_date(final_date);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " mensal_consum='" + getMensal_consum() + "'" +
            ", id='" + getId() + "'" +
            ", address='" + getAddress() + "'" +
            ", initial_date='" + getInitial_date() + "'" +
            ", final_date='" + getFinal_date() + "'" +
            "}";
    }

    public Invoice clone(){
        return new Invoice(this);
    }
}

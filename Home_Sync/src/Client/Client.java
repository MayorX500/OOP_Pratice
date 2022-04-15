package Client;

import House.House;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int client_id;
    private String client_name;
    private HashSet<House> houses;

    public Client(int id,String name, HashSet<House> houses){
        this.client_id=id;
        this.client_name=name;
        this.setHouses(houses);
    }

    public Client(String name,HashSet<House> houses){
        this(count.incrementAndGet(), name, houses);
    }

    public Client(){
        this("name", new HashSet<>());
    }

    public Client(Client o){
        this(o.getClient_id(),o.getClient_name(),o.getHouses());
    }

    public int getClient_id() {
        return this.client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return this.client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public HashSet<House> getHouses() {
        HashSet<House> out = new HashSet<>();
        for(House house: this.houses){
            out.add(house.clone());
        }
        return out;
    }

    public void setHouses(HashSet<House> houses) {
        HashSet<House> out = new HashSet<>();
        for(House house: houses){
            out.add(house.clone());
        }
        this.houses = out;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Client)) {
            return false;
        }
        Client client = (Client) o;
        return client_id == client.client_id && this.client_name.equals(client.getClient_name()) && this.houses.equals(client.getHouses());
    }


    @Override
    public String toString() {
        return "{" +
            " client_id='" + getClient_id() + "'" +
            ", client_name='" + getClient_name() + "'" +
            ", houses='" + getHouses().toString() + "'" +
            "}";
    }

}

package Client;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int client_id;
    private String client_name;
    private int nif;

    public Client(int id,String name, int nif){
        this.client_id=id;
        this.client_name=name;
        this.nif=nif;
    }

    public Client(String name,int nif){
        this(count.incrementAndGet(), name, nif);
    }

    public Client(){
        this("name", 0);
    }

    public Client(Client o){
        this(o.getClient_id(),o.getClient_name(),o.getClient_NIF());
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

    public int getClient_NIF() {
        return this.nif;
    }

    public void setClient_NIF(int nif) {
        this.nif = nif;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Client)) {
            return false;
        }
        Client client = (Client) o;
        return client_id == client.client_id && this.client_name.equals(client.getClient_name()) && this.nif == client.getClient_NIF();
    }

    public Client clone(){
        return new Client(this);
    }


    @Override
    public String toString() {
        return "{" +
            " client_id='" + getClient_id() + "'" +
            ", client_name='" + getClient_name() + "'" +
            ", houses='" + getClient_NIF() + "'" +
            "}";
    }

}

package House;

import Auxiliar.Pair;

public class Address {
    private String street;
    private int street_number;
    private String city;
    private Pair<Integer,Integer> post_code;

    public Address(String street, int street_number, String city, Pair<Integer,Integer> post_code) {
        this.street = street;
        this.street_number = street_number;
        this.city = city;
        this.post_code = post_code.clone();
    }

    public Address() {
        this("street", 0, "city", new Pair<Integer,Integer>(0, 0));
    }

    public Address(Address a){
        this(a.getStreet(),a.getStreet_number(),a.getCity(),a.getPost_code().clone());
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreet_number() {
        return this.street_number;
    }

    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Pair<Integer,Integer> getPost_code() {
        return this.post_code.clone();
    }

    public void setPost_code(Pair<Integer,Integer> post_code) {
        this.post_code = post_code.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return this.street.equals(address.getStreet()) && street_number == address.street_number && this.city.equals(address.getCity()) && this.post_code.equals(address.getPost_code());
    }

    @Override
    public Address clone(){
        return new Address(this);
    }
    @Override
    public String toString() {
        return "{" +
            " street='" + getStreet() + "'" +
            ", street_number='" + getStreet_number() + "'" +
            ", city='" + getCity() + "'" +
            ", post_code='" + getPost_code() + "'" +
            "}";
    }

}

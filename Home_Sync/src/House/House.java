package House;
import Suppliers.*;


import java.util.HashSet;
import java.util.concurrent.atomic.*;

public class House{
    private static final AtomicInteger count = new AtomicInteger(0); 
    private int house_id;
    private Address address;
    private HashSet<Divisions> divisions;
    private Suppliers supplier;
    private long max_consume;
    
    public House(int id,Address address, HashSet<Divisions> divisions, Suppliers supplier, long MaxConsume) {
	this.house_id = id;
        this.address = address.clone();
        this.setDivisions(divisions);
        this.supplier = supplier.clone();
        this.max_consume = MaxConsume;
    }
    public House(Address address, HashSet<Divisions> divisions, Suppliers supplier, long MaxConsume) {
	this.house_id = count.incrementAndGet();
        this.address = address.clone();
        this.setDivisions(divisions);
        this.supplier = supplier.clone();
        this.max_consume = MaxConsume;
    }

    public House() {
        this(new Address(),new HashSet<>(),new Suppliers(),0);
    }

    public House(House o) {
        this(o.getHouse_id(),o.getAddress(), o.getDivisions(), o.getSupplier(), o.getMaxConsume());
    }

    public int getHouse_id(){
	    return this.house_id;
    }

    public Address getAddress() {
        return this.address.clone();
    }

    public Suppliers getSupplier() {
        return this.supplier.clone();
    }

    public HashSet<Divisions> getDivisions(){
        HashSet<Divisions> out = new HashSet<>();
        for(Divisions a : this.divisions)
        out.add(a.clone());
        return out;
    }

    public long getMaxConsume() {
        return this.max_consume;
    }

    public void setHouse_id(int id){
	    this.house_id=id;
    }

    public void setAddress(Address address) {
        this.address = address.clone();
    }

    public void setDivisions(HashSet<Divisions> divisions) {
        HashSet<Divisions> out = new HashSet<>();
        for(Divisions a : this.divisions)
        out.add(a.clone());
        this.divisions = out;
    }

    public void setSupplier(Suppliers supplier) {
        this.supplier = supplier.clone();
    }

    public void setMax_Consume(long max_consume) {
        this.max_consume = max_consume;
        }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof House)) {
            return false;
        }
        House house = (House) o;
        return (this.house_id==house.getHouse_id() && this.address.equals(house.getAddress()) && this.max_consume == house.max_consume && this.supplier.equals(house.getSupplier()) && this.divisions.equals( house.getDivisions()));
    }

    @Override
    public House clone(){
        return new House(this);
    }
    
    @Override
    public String toString() {
        return "{" +
            " house_id='" + getHouse_id() + "'" +
            " address='" + getAddress().toString() + "'" +
            ", divisions='" + getDivisions() + "'" +
            ", supplier='" + getSupplier().toString() + "'" +
            ", MaxConsume='" + getMaxConsume() + "'" +
            "}";
    }
}

    //public void getDevices

   /* public void addDevice(String s,SmartDevice m) {
        if (divisions.get(s) == null) {
            ArrayList<SmartDevice> Arr = new ArrayList<SmartDevice>();
            Arr.add(m);
            divisions.put(s, Arr);
        } else {
            ArrayList<SmartDevice> Arr = divisions.get(s);
            Arr.add(m);
            divisions.put(s, Arr);
        }
    }

    public void turnAllOnOFF(boolean state){
        for(Map.Entry<String, ArrayList<SmartDevice>> entry : this.divisions.entrySet()) {
            for (SmartDevice dev : entry.getValue()) {
                dev.setIs_on(state);
            }
        }
    }

    public void turnOneOnOFF(String div,SmartDevice smart,boolean state){
        ArrayList<SmartDevice> Arr = divisions.get(div);
        int i =Arr.indexOf(smart);
        Arr.get(i).setIs_on(state);
        divisions.put(div, Arr);
    }



}


    //public void getDevices

    public void addDevice(String s,SmartDevice m) {

        if (divisions.get(s) == null) {
            ArrayList<SmartDevice> Arr = new ArrayList<SmartDevice>();
            Arr.add(m);
            divisions.put(s, Arr);
        } else {
            ArrayList<SmartDevice> Arr = divisions.get(s);
            Arr.add(m);
            divisions.put(s, Arr);
        }

    }

    public void turnAllOnOFF(boolean state){
        for(Map.Entry<String, ArrayList<SmartDevice>> entry : this.divisions.entrySet()) {
            for (SmartDevice dev : entry.getValue()) {
                dev.setIs_on(state);
            }
        }
    }

    public void divisionOnOff(String div,boolean state){

        ArrayList<SmartDevice> alldevices = this.divisions.get(div);

        for(SmartDevice device : alldevices){
            device.setIs_on(state);
        }
        this.divisions.put(div,alldevices);

    }

    public void turnOneOnOFF(String div,SmartDevice smart,boolean state){

        ArrayList<SmartDevice> Arr = this.divisions.get(div);

        int i =Arr.indexOf(smart);

        Arr.get(i).setIs_on(state);

        this.divisions.put(div, Arr);
    }


}
*/

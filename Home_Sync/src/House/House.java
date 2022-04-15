package House;
import SmartDevice.*;
import Suppliers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class House{
    private String address;
    private HashMap<String,ArrayList<SmartDevice>> divisions;
    private Suppliers supplier;
    private long max_consume;

    public House(String address, HashMap<String,ArrayList<SmartDevice>> divisions, Suppliers supplier, long MaxConsume) {
        this.address = address;
        this.setDivisions(divisions);
        this.supplier = supplier.clone();
        this.max_consume = MaxConsume;
    }

    public House() {
        this("NULL",new HashMap<>(),new Suppliers(),0);
    }

    public House(House o) {
        this(o.getAddress(), o.getDivisions(), o.getSupplier(), o.getMaxConsume());
    }

    public String getAddress() {
        return this.address;
    }

    public Suppliers getSupplier() {
        return this.supplier.clone();
    }

    public HashMap<String, ArrayList<SmartDevice>> getDivisions(){
        HashMap<String, ArrayList<SmartDevice>> out = new HashMap<>();
        for(Map.Entry<String, ArrayList<SmartDevice>> entry : this.divisions.entrySet()) {
            ArrayList<SmartDevice> arr = new ArrayList<>();
            String key = entry.getKey();
            for (SmartDevice dev : entry.getValue()) {
                arr.add(dev.clone());
            }
            out.put(key, arr);
        }
        return out;
    }

    public long getMaxConsume() {
        return this.max_consume;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDivisions(HashMap<String,ArrayList<SmartDevice>> divisions) {
        HashMap<String, ArrayList<SmartDevice>> out = new HashMap<>();
        for(Map.Entry<String, ArrayList<SmartDevice>> entry : divisions.entrySet()) {
            ArrayList<SmartDevice> arr = new ArrayList<>();
            String key = entry.getKey();
            for (SmartDevice dev : entry.getValue()) {
                arr.add(dev.clone());
            }
            out.put(key, arr);
            }
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
        return (this.max_consume == house.max_consume && this.supplier == house.supplier && this.divisions.keySet().equals( house.getDivisions().keySet()) && (new ArrayList<>( this.divisions.values() ).equals(new ArrayList<>( house.getDivisions().values() ))));
    }

    @Override
    public String toString() {
        return "{" +
            " address='" + getAddress() + "'" +
            ", divisions='" + getDivisions() + "'" +
            ", supplier='" + getSupplier().toString() + "'" +
            ", MaxConsume='" + getMaxConsume() + "'" +
            "}";
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
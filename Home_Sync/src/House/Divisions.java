package House;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import Exceptions.Empty_Division;
import SmartDevice.SmartDevice;

public class Divisions {
    private static final AtomicInteger count = new AtomicInteger(0); 
    private String division_name;
    private HashSet<SmartDevice> devices;

    public Divisions(String division_name, HashSet<SmartDevice> devices) {
        this.division_name = division_name;
        this.devices = devices;
    }

    public Divisions() {
        this("DIVISION " + count.incrementAndGet(),new HashSet<>());
    }

    public Divisions(Divisions d) {
        this(d.getDivision_name(),d.getDevices());
    }

    public String getDivision_name() {
        return this.division_name;
    }

    public void setDivision_name(String division_name) {
        this.division_name = division_name;
    }

    public HashSet<SmartDevice> getDevices() {
        return this.devices;
    }

    public void setDevices(HashSet<SmartDevice> devices) {
        this.devices = devices;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Divisions)) {
            return false;
        }
        Divisions divisions = (Divisions) o;
        return this.division_name.equals(divisions.getDivision_name()) && this.devices.equals(divisions.getDevices());
    }

    @Override
    public Divisions clone(){
        return new Divisions(this);
    }

    @Override
    public String toString() {
        return "{" +
            " division_name='" + getDivision_name() + "'" +
            ", devices='" + getDevices() + "'" +
            "}";
    }

    public void manageDivision(boolean state) throws Empty_Division{
        HashSet<SmartDevice> list = new HashSet<>();

        if(this.devices.size()>1){
            for(SmartDevice e : this.devices){
                if(e.getIs_on() != state){
                    e.setIs_on(state);
                }
                list.add(e.clone());
            }
            this.devices=list;
        }
        else throw new Empty_Division("Empty Division");
    }

    public double getDaily_Division_Power_Usage() throws Empty_Division{
        double out = 0;
        if(this.devices.size()>1){
            for(SmartDevice e : this.devices){
                out += e.getPower_usage();
            }
        }
        else throw new Empty_Division("Empty Division");
        return out;
    }

}
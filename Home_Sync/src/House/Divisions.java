package House;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

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

}
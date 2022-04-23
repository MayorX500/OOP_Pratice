package House;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import Exceptions.*;
import SmartDevice.*;

public class Divisions {
    private static final AtomicInteger count = new AtomicInteger(0); 
    private String division_name;
    private Set<SmartDevice> devices;

    public Divisions(String division_name, Set<SmartDevice> devices) {
        this.division_name = division_name;
        this.setDevices(devices);
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

    public Set<SmartDevice> getDevices() {
	    Set<SmartDevice> out = new HashSet<>();
	    if(this.devices.size()>0){
		    for(SmartDevice dev : this.devices){
			    out.add(dev.clone());
		    }
	    }
	    return out;
    }

    public void setDevices(Set<SmartDevice> devices) {
 	    Set<SmartDevice> out = new HashSet<>();
	    if(devices.size()>0){
		    for(SmartDevice dev : devices){
			    out.add(dev.clone());
		    }
	    }
	    this.devices = out;
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
        Set<SmartDevice> list = new HashSet<>();

        if(this.devices.size()>1){
            for(SmartDevice e : this.devices){
                if(e.getIs_on() != state){
                    e.setIs_on(state);
                }
                list.add(e.clone());
            }
            this.devices=list;
        }
        else throw new Empty_Division( this.division_name + " - Empty Division");
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

    public void addDevice(SmartDevice dev){
	    Set<SmartDevice> new_set = new HashSet<>();
	    new_set.add(dev.clone());
	    if(this.getDevices().size()>0){
		    for(SmartDevice device : this.getDevices()){
			    new_set.add(device.clone());
		    }
	    }
	    setDevices(new_set);
    }

    public void manageDevice(SmartDevice dev, boolean state) throws State_Not_Changed,Device_Non_Existent,Empty_Division{
	    if(this.devices.size()>0){
		    for(SmartDevice device : this.devices){
			    if(device.equals(dev)){
				    if(device.getIs_on() != state){
					    device.setIs_on(state);
				    }
				    else throw new State_Not_Changed(device.getDevice_name());
			    }
			    else throw new Device_Non_Existent(dev.getDevice_name()); 
		    }
	    }
	    else throw new Empty_Division(this.division_name);
    }
}


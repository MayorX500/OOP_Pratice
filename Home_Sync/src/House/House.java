package House;
import Suppliers.*;
import Client.*;
import Exceptions.*;
import SmartDevice.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.*;

public class House implements Serializable, Comparable<House>{
    private static final AtomicInteger count = new AtomicInteger(0); 
    private int house_id;
    private Address address;
    private Client owner;
    private Set<Divisions> divisions;
    private Suppliers supplier;
    private double daily_consumption;
    
    public House(int id,Address address, Client owner, Set<Divisions> divisions, Suppliers supplier, double daily_consumption) {
	this.house_id = id;
        this.address = address;
        this.owner = owner;
        this.setDivisions(divisions);
        this.supplier = supplier;
        this.daily_consumption = daily_consumption;
    }
    public House(Address address, Client owner, Set<Divisions> divisions, Suppliers supplier, double daily_consumption) {
	    this(count.incrementAndGet(), address, owner, divisions, supplier, daily_consumption);
    }

    public House() {
        this(new Address(), new Client(), new HashSet<>(),new Suppliers(),0);
    }

    public House(House o) {
        this(o.getHouse_id(), o.getAddress(), o.getOwner(), o.getDivisions(), o.getSupplier(), o.getDaily_consumption());
    }

    public int getHouse_id(){
	    return this.house_id;
    }

    public Address getAddress() {
        return this.address;
    }

    public Client getOwner() {
        return owner;
    }

    public Suppliers getSupplier() {
        return this.supplier;
    }

    public Set<Divisions> getDivisions(){
		Set<Divisions> out = new HashSet<>();
        for(Divisions a : this.divisions)
		out.add(a);
        return out;
    }

    public double getDaily_consumption() {
        return this.daily_consumption;
    }

    public void setHouse_id(int id){
	    this.house_id=id;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public void setDivisions(Set<Divisions> divisions) {
        Set<Divisions> out = new HashSet<>();
        for(Divisions a : divisions){
		out.add(a);
		try{
			this.daily_consumption += a.getDaily_Division_Power_Usage();
		}catch(Empty_Division e){
			this.daily_consumption += 0 ;
		}
	}
        this.divisions = out;
    }

    public void setSupplier(Suppliers supplier) {
        this.supplier = supplier;
    }

    public void setDaily_Consumption(double daily_consumption) {
        this.daily_consumption = daily_consumption;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof House)) {
            return false;
        }
        House house = (House) o;
        return (this.house_id==house.getHouse_id() && this.address.equals(house.getAddress()) && this.daily_consumption == house.daily_consumption && this.supplier.equals(house.getSupplier()) && this.divisions.equals( house.getDivisions()));
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
            " address='" + getOwner().toString() + "'" +
            ", divisions='" + getDivisions() + "'" +
            ", supplier='" + getSupplier().toString() + "'" +
            ", daily_consumption='" + getDaily_consumption() + "'" +
            "}";
    }

	public int compareTo(House h){
		return this.getSupplier().getSupplier_name().compareTo(h.getSupplier().getSupplier_name());
	}

    //Non_Basic_Methods<

    public double getHouse_daily_Price() throws Empty_House{
	    double pu = 0.0;
	    if (this.divisions.size()>0) {
		    for(Divisions e: getDivisions()){
			    try{
				    pu += e.getDaily_Division_Power_Usage();
			    }
			    catch(Empty_Division e1){
				    pu += 0;
			    }
		    }
		    return (pu * supplier.getSupplier_rate());
	    }
	    else throw new Empty_House("The house at "+ this.getAddress().toString() +" is empty");
    }

	public void addDivision(Divisions div){
		Set<Divisions> list = this.getDivisions();
		list.add(div);
		this.setDivisions(list);
	}

    public void addDevice(String division,SmartDevice device) throws Division_Non_Existent {
	    for(Divisions div : this.getDivisions()){
		    if(div.getDivision_name().equals(division)){
			    div.addDevice(device);
				this.daily_consumption += device.getPower_usage();
			}
		    else throw new Division_Non_Existent(division);
	    }
    }

    public void change_house_state(boolean state) throws Empty_House,Empty_Division{
	    if(this.divisions.size()>0){
		    for(Divisions div : this.divisions){
			    try{
				    div.manageDivision(state);
			    }
			    catch(Empty_Division e1){
				    throw e1;
			    }
		    }
	    }
	    else throw new Empty_House("The house at "+ this.getAddress().toString() +" is empty");
    } 


    public void change_division_state(String division,boolean state) throws Empty_Division,Division_Non_Existent,Empty_House{
	    if(this.divisions.size()>0){
		    for(Divisions div : this.divisions){
			    try{
				    if(div.getDivision_name().equals(division))
					    div.manageDivision(state);
				    else throw new Division_Non_Existent(division);
			    }catch(Empty_Division e1){
				    throw e1;
			    }
		    }
	    }
	    else throw new Empty_House("The house at "+ this.getAddress().toString() +" is empty");
    }

    public void change_device_state(SmartDevice dev, boolean state) throws Device_Non_Existent, State_Not_Changed, Empty_Division, Empty_House{
	    if(this.divisions.size()>0){
		    for(Divisions div : this.divisions){
			    try{
				    if((div.getDevices().size()>0)&&div.getDevices().contains(dev)){
					    try{
						    div.manageDevice(dev,state);
					    }
					    catch(State_Not_Changed s1){
						    throw s1;
					    }
					    catch(Device_Non_Existent s2){
						    throw s2;
					    }
					    catch(Empty_Division s3){
						    throw s3;
					    }
				    }
				    else throw new Device_Non_Existent(dev.getDevice_name());
			    }catch(Empty_Division e){
				    throw e;
			    }
		    }
	    }
	    else throw new Empty_House("The house at "+ this.getAddress().toString() +" is empty");
    }

    public void change_device_state(String dev_name, boolean state) throws Device_Non_Existent, State_Not_Changed, Empty_Division, Empty_House{
	    if(this.divisions.size()>0){
		    for(Divisions div : this.divisions){
			    Set<SmartDevice> devices_from_division =  div.getDevices();
			    if(devices_from_division.size()>0){
				    for(SmartDevice dev :devices_from_division){
					    if(dev.getDevice_name().equals(dev_name)){
						    try{
							    div.manageDevice(dev,state);
						    }
						    catch(State_Not_Changed s1){
							    throw s1;
						    }
						    catch(Device_Non_Existent s2){
							    throw s2;
						    }
						    catch(Empty_Division s3){
							    throw s3;
						    }
					    }
					    else throw new Device_Non_Existent(dev_name);
				    }
			    }
			    throw new Empty_Division(div.getDivision_name());
		    }
	    }
	    else throw new Empty_House("The house at "+ this.getAddress().toString() +" is empty");
    }

    public void change_device_state(int dev_id, boolean state) throws Device_Non_Existent, State_Not_Changed, Empty_Division, Empty_House{
		boolean flag = true;
	    if(this.divisions.size()>0){
		    for(Divisions div : this.divisions){
			    Set<SmartDevice> devices_from_division =  div.getDevices();
			    if(devices_from_division.size()>0){
				    for(SmartDevice dev :devices_from_division){
					    if(dev.getDevice_id() == dev_id){
						    try{
							    div.manageDevice(dev,state);
						    }
						    catch(State_Not_Changed s1){
							    throw s1;
						    }
						    catch(Device_Non_Existent s2){
							    throw s2;
						    }
						    catch(Empty_Division s3){
							    throw s3;
						    }
							flag = false;
					    }
				    }
			    }
			    throw new Empty_Division(div.getDivision_name());
		    }
	    }
	    else throw new Empty_House("The house at "+ this.getAddress().toString() +" is empty");
		if (flag) throw new Device_Non_Existent(dev_id);
    }

}

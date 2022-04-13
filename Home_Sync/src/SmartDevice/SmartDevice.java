package SmartDevice;

import java.time.LocalDate;
import java.util.concurrent.atomic.*;

public class SmartDevice {

	private static final AtomicInteger count = new AtomicInteger(0); 
	private int device_id;
	private boolean is_on;
	private LocalDate installed_on;
	private String device_name;
	private String brand;
	private double power_usage;

	public SmartDevice(int device_id,boolean is_on,LocalDate installed_on,String device_name, String brand,double power_usage){
		this.device_id = device_id;
		this.is_on=is_on;
		this.installed_on = installed_on;
		this.device_name=device_name;
		this.brand=brand;
		this.power_usage=power_usage;
	}

	public SmartDevice(boolean is_on,LocalDate installed_on,String device_name, String brand,double power_usage){
		this(count.incrementAndGet(),is_on,installed_on,device_name,brand,power_usage);
	}
  
	public SmartDevice(){
		this(false,LocalDate.now(), "SmartDevice", "Brand", 0.0);
	}
  
	public SmartDevice(SmartDevice o){
		this(o.getDevice_id(),
				o.getIs_on(),
				o.getInstalled_on(),
				o.getDevice_name(),
				o.getBrand(),
				o.getPower_usage());
	}

	public int getDevice_id() {
		return this.device_id;
	}

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}

	public boolean is_on() {
		return this.is_on;
	}

	public boolean getIs_on() {
		return this.is_on;
	}

	public void setIs_on(boolean is_on) {
		this.is_on = is_on;
	}

	public LocalDate getInstalled_on(){
		return this.installed_on;
	}

	public void setInstalled_on(LocalDate installed_on){
		this.installed_on = installed_on;
	}

	public String getDevice_name() {
		return this.device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPower_usage() {
		return this.power_usage;
	}

	public void setPower_usage(double power_usage) {
		this.power_usage = power_usage;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof SmartDevice)) {
			return false;
		}
		SmartDevice smartDevice = (SmartDevice) o;
		return (this.device_id == smartDevice.device_id &&
			this.is_on == smartDevice.is_on &&
			this.installed_on.equals(smartDevice.getInstalled_on()) &&
			this.device_name.equals(smartDevice.device_name) &&
			this.brand.equals(smartDevice.brand) &&
			this.power_usage == smartDevice.power_usage);
	}

	@Override
	public SmartDevice clone(){
		return new SmartDevice(this);
	}

	@Override
	public String toString() {
		return "{" +
			" device_id='" + getDevice_id() + "'" +
			", is_on='" + is_on() + "'" +
			", installed_on='" + getInstalled_on().toString() + "'" +
			", device_name='" + getDevice_name() + "'" +
			", brand='" + getBrand() + "'" +
			", power_usage='" + getPower_usage() + "'" +
			"}";
	}

}

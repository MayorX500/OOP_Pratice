package SmartDevice;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import Auxiliar.Consumption;
import Auxiliar.Pair;
import Auxiliar.State;

public class SmartCamera extends SmartDevice implements Consumption{
    private Pair<Integer,Integer> resolution;
    private double file_size;

    public SmartCamera() {
        this(false, LocalDateTime.now(), "SmartCamera", "OOP_Camera", 36.0,2.5,new HashSet<>(), new Pair<Integer,Integer>(0, 0), 0.0);
    }

    public SmartCamera(String device_name, String brand, Pair<Integer,Integer> resolution, double file_size) {
        this(false, LocalDateTime.now(), device_name, brand, 3.6 * resolution.getL() * resolution.getR(), 2.5 * resolution.getL() * resolution.getR(), new HashSet<>(), resolution, file_size);
    }

    public SmartCamera(String device_name, Pair<Integer,Integer> resolution, double file_size, float consumption) {
        this(false, LocalDateTime.now(), device_name, "OOP_SmartDevices", consumption * resolution.getL() * resolution.getR(), 2.5 * resolution.getL() * resolution.getR(), new HashSet<>(), resolution, file_size);
    }

    public SmartCamera(boolean is_on,LocalDateTime installed_on,String device_name, String brand,double power_usage, double base_cost, Set<State> log, Pair<Integer,Integer> resolution, double file_size) {
        super(is_on, installed_on, device_name, brand, power_usage,base_cost,log,0);
        this.resolution = resolution;
        this.file_size = file_size;

    }
    
    public SmartCamera(int device_id,boolean is_on,LocalDateTime installed_on,String device_name, String brand,double power_usage,double base_cost,Set<State> log,Pair<Integer,Integer> resolution, double file_size,int time_on) {
        super(device_id, is_on, installed_on, device_name, brand, power_usage,base_cost,log,time_on);
        this.resolution = resolution;
        this.file_size = file_size;
    }

    public SmartCamera(SmartCamera o){
		this(o.getDevice_id(), o.getIs_on(),o.getInstalled_on(), o.getDevice_name(), o.getBrand(),o.getPower_usage(),o.getBase_cost(),o.getLog(),o.getResolution(), o.getFile_size(),o.getTime_on());
    }

    public Pair<Integer,Integer> getResolution() {
        return this.resolution;
    }

    public void setResolution(Pair<Integer,Integer> resolution) {
        this.resolution = resolution;
    }

    public double getFile_size() {
        return this.file_size;
    }

    public void setFile_size(double file_size) {
        this.file_size = file_size;
    }
    
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SmartCamera)) {
            return false;
        }
        SmartCamera smartCamera = (SmartCamera) o;
        return resolution == smartCamera.resolution && file_size == smartCamera.file_size;
    }

    public SmartCamera clone () {
        return new SmartCamera(this);
    }

    public String toString() {
        return "{" +
            " resolution= " + getResolution() + " " +
            ", file_size= " + getFile_size() + " " +
            ", power_usage= " + getPower_usage() + " " +
            "}";
    }

    public double getPower_usage() {
		return super.getPower_usage()*(this.getFile_size()*this.getResolution().getL()*this.getResolution().getR()*0.0001);
	}

}


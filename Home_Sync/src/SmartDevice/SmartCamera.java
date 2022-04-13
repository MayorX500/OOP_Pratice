package SmartDevice;

import java.time.LocalDate;

public class SmartCamera extends SmartDevice{
    private double resolution;
    private double file_size;

    public SmartCamera() {
        this(false, LocalDate.now(), "SmartCamera", "OOP_Camera", 0.0, 0.0, 0.0);
    }

    public SmartCamera(boolean is_on,LocalDate installed_on,String device_name, String brand,double power_usage,double resolution, double file_size) {
        super(is_on, installed_on, device_name, brand, power_usage);
        this.resolution = resolution;
        this.file_size = file_size;

    }
    
    public SmartCamera(int device_id,boolean is_on,LocalDate installed_on,String device_name, String brand,double power_usage,double resolution, double file_size) {
        super(device_id, is_on, installed_on, device_name, brand, power_usage);
        this.resolution = resolution;
        this.file_size = file_size;
    }

    public SmartCamera(SmartCamera o){
		this(o.getDevice_id(), o.getIs_on(),o.getInstalled_on(), o.getDevice_name(), o.getBrand(),o.getPower_usage(),o.getResolution(), o.getFile_size());
    }

    public double getPower_usage(){
        return this.resolution * this.file_size;
    }

    public double getResolution() {
        return this.resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public double getFile_size() {
        return this.file_size;
    }

    public void setFile_size(double file_size) {
        this.file_size = file_size;
    }

    public SmartCamera resolution(double resolution) {
        setResolution(resolution);
        return this;
    }

    public SmartCamera file_size(double file_size) {
        setFile_size(file_size);
        return this;
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

}


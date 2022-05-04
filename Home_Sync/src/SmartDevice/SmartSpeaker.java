package SmartDevice;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import Auxiliar.Consumption;
import Auxiliar.State;

public class SmartSpeaker extends SmartDevice implements Consumption{
    private int volume;
    private String radio_info;

    public SmartSpeaker() {
        this(false, LocalDateTime.now(), "SmartSpeaker", "OOP_Speaker", 0.0, 0.0, 0,"NULL",new HashSet<>());
    }

    public SmartSpeaker(String device_name, String brand, int volume,float consumption, String radio_info) {
        this(false, LocalDateTime.now(), device_name, brand, 36*consumption, 2.5*consumption, volume, radio_info, new HashSet<>());
    }

    public SmartSpeaker(int device_id,boolean is_on,LocalDateTime installed_on,String device_name, String brand,double power_usage, double base_cost, int volume, String radio_info, Set<State> log,int time_on) {
        super(device_id, is_on, installed_on, device_name, brand, power_usage, base_cost, log,time_on);
        this.volume = volume;
        this.radio_info = radio_info;
    }

    public SmartSpeaker(boolean is_on,LocalDateTime installed_on,String device_name, String brand,double power_usage, double base_cost, int volume, String radio_info,Set<State> log) {
        super(is_on, installed_on, device_name, brand, power_usage, base_cost, log,0);
        this.volume = volume;
        this.radio_info = radio_info;
    }
  

    public SmartSpeaker(SmartSpeaker o){
        this(o.getDevice_id(), o.getIs_on(), o.getInstalled_on(), o.getDevice_name(), o.getBrand(), o.getPower_usage(), o.getBase_cost(), o.getVolume(), o.getRadio_info(), o.getLog(),o.getTime_on());
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getRadio_info() {
        return this.radio_info;
    }

    public void setRadio_info(String radio_info) {
        this.radio_info = radio_info;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SmartSpeaker)) {
            return false;
        }
        SmartSpeaker smartSpeaker = (SmartSpeaker) o;
        return volume == smartSpeaker.volume && this.radio_info.equals(smartSpeaker.radio_info);
    }

    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }

    @Override
    public String toString() {
        return "{" +
            " volume='" + getVolume() + "'" +
            ", radio_info='" + getRadio_info() + "'" +
            "}";
    }

    public double getPower_usage() {
		return super.getPower_usage()*(this.getVolume()*0.1);
	}


}

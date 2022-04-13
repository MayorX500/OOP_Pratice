package SmartDevice;

import java.time.LocalDate;

public class SmartSpeaker extends SmartDevice {
    private int volume;
    private String radio_info;

    public SmartSpeaker() {
        this(0,false, LocalDate.now(), "SmartSpeaker", "OOP_Speaker", 0.0, "NULL", 0,"NULL");
    }

    public SmartSpeaker(int device_id,boolean is_on,LocalDate installed_on,String device_name, String brand,double power_usage, String location, int volume, String radio_info) {
        super(device_id, is_on, installed_on, device_name, brand, power_usage, location);
        this.volume = volume;
        this.radio_info = radio_info;
    }  

    public SmartSpeaker(SmartSpeaker o){
        this(o.getDevice_id(), o.getIs_on(),o.getInstalled_on(), o.getDevice_name(), o.getBrand(),o.getPower_usage(),o.getLocation(),o.getVolume(),o.getRadio_info());
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

    public SmartSpeaker volume(int volume) {
        setVolume(volume);
        return this;
    }

    public SmartSpeaker radio_info(String radio_info) {
        setRadio_info(radio_info);
        return this;
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


}
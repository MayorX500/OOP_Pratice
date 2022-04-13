package SmartDevice;

import java.time.LocalDate;
import java.util.HashMap;

public class SmartBulb extends SmartDevice{
	private Tonality color;
	private  float dimension;
	private HashMap<LocalDate,String> log;
	
	public SmartBulb(int device_id,boolean is_on,LocalDate installed_on,String device_name, String brand,double power_usage, String location, Tonality color, float dimension, HashMap<LocalDate,String> log) {
		super(device_id, is_on, installed_on, device_name, brand, power_usage, location);
		this.color = color;
		this.dimension = dimension;
		this.log = log;
	}

	public SmartBulb(boolean is_on,LocalDate installed_on,String device_name, String brand,double power_usage, String location, Tonality color, float dimension, HashMap<LocalDate,String> log) {
		super(is_on, installed_on, device_name, brand, power_usage, location);
		this.color = color;
		this.dimension = dimension;
		this.log = log;
	}

	public SmartBulb(){
		this(false,LocalDate.now(),"SmartBulb","OOP_Bulbs",9.0,"NULL",Tonality.OFF,6.0f,new HashMap<>());
	}
	
	public SmartBulb(SmartBulb o){
		this(o.getDevice_id(), o.getIs_on(),o.getInstalled_on(), o.getDevice_name(), o.getBrand(),o.getPower_usage(),o.getLocation(),o.getColor(),o.getDimension(),o.getlog());
	}

	private enum Tonality{
		OFF(0.0f){
			public String toString() {
				return "OFF";
			  }
		},
		NEUTRAL(1.0f){
			public String toString() {
				return "Neutral";
			  }
		},
		WARM(1.06f){
			public String toString() {
				return "Warm";
			  }
		},
		COLD(1.1f){
			public String toString() {
				return "Cold";
			  }
		};

		public final float consumption;
	
		private Tonality(float consumption){
			this.consumption=consumption;

		}

		public float getConsumption(){
			return this.consumption;
		}
		public boolean _equals(Object o){
			if (o == this)
			return true;
			if (!(o instanceof Tonality))
			return false;
			Tonality Color = (Tonality) o;
			return this.getConsumption() == Color.getConsumption();
		}
	}

	public Tonality getColor() {
		return this.color;
	}

	public void setColor(Tonality color) {
		this.color = color;
	}

	public float getDimension() {
		return this.dimension;
	}

	public void setDimension(float dimension) {
		this.dimension = dimension;
	}

	public HashMap<LocalDate,String> getlog() {
		return this.log;
	}

	public void setlog(HashMap<LocalDate,String> log) {
		this.log = log;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof SmartBulb)) {
			return false;
		}
		SmartBulb smartBulb = (SmartBulb) o;
		return this.color._equals(smartBulb.color) && dimension == smartBulb.dimension && this.log.equals(smartBulb.log);
	}

	@Override
	public SmartBulb clone(){
		return new SmartBulb(this);
	}

	@Override
	public String toString() {
		return "{" +
			" color='" + getColor().toString() + "'" +
			", dimension='" + getDimension() + "'" +
			", log='" + getlog() + "'" +
			"}";
	}

}

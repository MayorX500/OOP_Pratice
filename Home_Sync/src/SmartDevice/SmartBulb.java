package SmartDevice;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import Auxiliar.Consumption;
import Auxiliar.State;

public class SmartBulb extends SmartDevice implements Consumption{
	private Tonality color;
	private  float dimension;
	
	public SmartBulb(int device_id,boolean is_on,LocalDateTime installed_on,String device_name, String brand,double power_usage, double base_cost, Tonality color, float dimension, Set<State> log, int time_on) {
		super(device_id, is_on, installed_on, device_name, brand, power_usage, base_cost, log,time_on);
		this.color = color;
		this.dimension = dimension;
	}

	public SmartBulb(boolean is_on,LocalDateTime installed_on,String device_name, String brand,double power_usage, double base_cost, Tonality color, float dimension, Set<State> log) {
		super(is_on, installed_on, device_name, brand, power_usage, base_cost, log,0);
		this.color = color;
		this.dimension = dimension;
	}

	public SmartBulb(){
		this(false,LocalDateTime.now(),"SmartBulb","OOP_Bulbs",216.0,15.0,Tonality.OFF,6.0f,new HashSet<>());
	}
	
	public SmartBulb(String device_name, String brand ,float dimension){
		this(false,LocalDateTime.now(),device_name,brand,36.0*dimension,2.5*dimension,Tonality.OFF,dimension,new HashSet<>());
	}

	public SmartBulb(String device_name, String t, float dimension,float consumption){
		this(false,LocalDateTime.now(),device_name,"OOP_SmartDevices",36.0*consumption,2.5*dimension,Tonality.OFF,dimension,new HashSet<>());
		Tonality color = Tonality.OFF;
		switch (t){
			case "Neutral" -> {
				color = Tonality.NEUTRAL;
			}
			case "Warm" -> {
				color = Tonality.WARM;
			}
			case "Cold" -> {
				color = Tonality.COLD;
			}
		}
		this.setColor(color);
	}

	public SmartBulb(SmartBulb o){
		this(o.getDevice_id(), o.getIs_on(),o.getInstalled_on(), o.getDevice_name(), o.getBrand(),o.getPower_usage(),o.getBase_cost(),o.getColor(),o.getDimension(),o.getLog(),o.getTime_on());
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

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof SmartBulb)) {
			return false;
		}
		SmartBulb smartBulb = (SmartBulb) o;
		return this.color._equals(smartBulb.color) && dimension == smartBulb.dimension;
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
			"}";
	}

	public double getPower_usage() {
		return super.getPower_usage()*this.getColor().getConsumption();
	}

}

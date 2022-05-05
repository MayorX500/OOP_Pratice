package Suppliers;

import java.io.Serializable;
import java.util.concurrent.atomic.*;

import Auxiliar.MyRandom;

public class Suppliers implements Serializable {
	private static final AtomicInteger count = new AtomicInteger(0);
	private int supplier_id;
	private String supplier_name;
	private float base_price;
	private float tax;
	private float out_of_range_tax;

	public Suppliers(int supplier_id, String supplier_name, float base_price, float tax, float out_of_range_tax) {
		this.supplier_id = supplier_id;
		this.supplier_name = supplier_name;
		this.base_price = base_price;
		this.tax = tax;
		this.out_of_range_tax = out_of_range_tax;
	}

	public Suppliers(String supplier_name, float base_price, float tax, float out_of_range_tax) {
		this.supplier_id = count.incrementAndGet();
		this.supplier_name = supplier_name;
		this.base_price = base_price;
		this.tax = tax;
		this.out_of_range_tax = out_of_range_tax;
	}

	public Suppliers(){
		this("Supplier", 1.0f, 0.13f, 0.05f);
	}
	public Suppliers(String s){
		this(s,MyRandom.random_f(0.5f,0.75f),MyRandom.random_f(0.001f, 0.05f),MyRandom.random_f(0.0f,0.005f));
	}

	public Suppliers(Suppliers o){
		this(o.getSupplier_id(),o.getSupplier_name(),o.getBase_price(),o.getTax(),o.getOut_of_range_tax());
	}

	public int getSupplier_id() {
		return this.supplier_id;
	}

	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier_name() {
		return this.supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public float getBase_price() {
		return this.base_price;
	}

	public void setBase_price(float base_price) {
		this.base_price = base_price;
	}

	public float getTax() {
		return this.tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getOut_of_range_tax() {
		return this.out_of_range_tax;
	}

	public void setOut_of_range_tax(float out_of_range_tax) {
		this.out_of_range_tax = out_of_range_tax;
	}

	public Suppliers clone(){
		return new Suppliers(this);
	}

	public float getSupplier_rate(){
		return(this.base_price*(this.tax+this.out_of_range_tax));
	}
}

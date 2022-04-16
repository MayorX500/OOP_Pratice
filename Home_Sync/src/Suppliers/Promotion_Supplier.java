package Suppliers;

public class Promotion_Supplier extends Suppliers{
	private float promotion_price;


	public Promotion_Supplier(int supplier_id, String supplier_name, float base_price, float tax, float out_of_range_tax,float promotion_price) {
		super(supplier_id,supplier_name,base_price,tax,out_of_range_tax);
		this.promotion_price = promotion_price;
	}

	public Promotion_Supplier(String supplier_name, float base_price, float tax, float out_of_range_tax,float promotion_price) {
		super(supplier_name,base_price,tax,out_of_range_tax);
		this.promotion_price = promotion_price;
	}

	public Promotion_Supplier() {
		this("Promotion",1f,0.13f,0.05f,0.75f);
	}

	public Promotion_Supplier(Promotion_Supplier o){
		this(o.getSupplier_id(),o.getSupplier_name(),o.getBase_price(),o.getTax(),o.getOut_of_range_tax(),o.getPromotion_price());
	}

	public Promotion_Supplier(Suppliers o,float promotion_price){
		this(o.getSupplier_id(),o.getSupplier_name(),o.getBase_price(),o.getTax(),o.getOut_of_range_tax(),promotion_price);
	}

	public float getPromotion_price() {
		return this.promotion_price;
	}

	public void setPromotion_price(float promotion_price) {
		this.promotion_price = promotion_price;
	}

	public Promotion_Supplier promotion_price(float promotion_price) {
		setPromotion_price(promotion_price);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Promotion_Supplier)) {
			return false;
		}
		Promotion_Supplier promotion_Supplier = (Promotion_Supplier) o;
		return promotion_price == promotion_Supplier.promotion_price;
	}

	@Override
	public String toString() {
		return "{" +
			" promotion_price='" + getPromotion_price() + "'" +
			"}";
	}

	@Override
	public Promotion_Supplier clone(){
		return new Promotion_Supplier(this);
	}

	public float getSupplier_rate(){
		return(this.getPromotion_price()*(this.getTax()+this.getOut_of_range_tax()));
	}
}

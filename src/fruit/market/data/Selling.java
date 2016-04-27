package fruit.market.data;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Selling {
	
	private String selling_id;

	private String good_id;
	
	private String store_id;
	
	private String value_unit;
	
	private BigDecimal out_price;
	
	private String package_type;
	
	private BigDecimal package_weight;
	
	private BigDecimal package_deposit;
	
	private String good_status;
	
	private String create_time;
	
	private Timestamp update_time;

	public String getSelling_id() {
		return selling_id;
	}

	public void setSelling_id(String selling_id) {
		this.selling_id = selling_id;
	}

	public String getGood_id() {
		return good_id;
	}

	public void setGood_id(String good_id) {
		this.good_id = good_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getValue_unit() {
		return value_unit;
	}

	public void setValue_unit(String value_unit) {
		this.value_unit = value_unit;
	}

	public BigDecimal getOut_price() {
		return out_price;
	}

	public void setOut_price(BigDecimal out_price) {
		this.out_price = out_price;
	}

	public String getPackage_type() {
		return package_type;
	}

	public void setPackage_type(String package_type) {
		this.package_type = package_type;
	}

	public BigDecimal getPackage_weight() {
		return package_weight;
	}

	public void setPackage_weight(BigDecimal package_weight) {
		this.package_weight = package_weight;
	}

	public BigDecimal getPackage_deposit() {
		return package_deposit;
	}

	public void setPackage_deposit(BigDecimal package_deposit) {
		this.package_deposit = package_deposit;
	}

	public String getGood_status() {
		return good_status;
	}

	public void setGood_status(String good_status) {
		this.good_status = good_status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
}

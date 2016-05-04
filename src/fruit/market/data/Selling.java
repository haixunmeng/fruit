package fruit.market.data;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Selling {
	
	private String good_id;
	
	private String store_id;
	
	private String value_unit;
	
	private BigDecimal sale_price;
	
	private String good_status;
	
	private Timestamp create_time;
	
	private Timestamp update_time;

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

	public BigDecimal getSale_price() {
		return sale_price;
	}

	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}

	public String getGood_status() {
		return good_status;
	}

	public void setGood_status(String good_status) {
		this.good_status = good_status;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
}

package fruit.market.data;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Stock {

	private String stock_id;
	
	private String store_id;
	
	private String good_id;
	
	private String value_unit;
	
	private BigDecimal left_num;
	
	private String create_time;
	
	private String update_time;

	public String getStock_id() {
		return stock_id;
	}

	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getGood_id() {
		return good_id;
	}

	public void setGood_id(String good_id) {
		this.good_id = good_id;
	}

	public String getValue_unit() {
		return value_unit;
	}

	public void setValue_unit(String value_unit) {
		this.value_unit = value_unit;
	}

	public BigDecimal getLeft_num() {
		return left_num;
	}

	public void setLeft_num(BigDecimal left_num) {
		this.left_num = left_num;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
}

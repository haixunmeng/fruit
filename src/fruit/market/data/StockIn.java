package fruit.market.data;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class StockIn {

	private String stock_in_batch_no;

	private String store_id;

	private BigDecimal total_price;
	
	private String remark;

	private Timestamp create_time;

	public String getStock_in_batch_no() {
		return stock_in_batch_no;
	}

	public void setStock_in_batch_no(String stock_batch_no) {
		this.stock_in_batch_no = stock_batch_no;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public BigDecimal getTotal_price() {
		return total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
}

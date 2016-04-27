package fruit.market.data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class StockIn {

	private String stock_batch_no;

	private String store_id;

	private BigDecimal good_price;

	private BigDecimal deposit;

	private BigDecimal total_price;

	private Timestamp create_time;

	public String getStock_batch_no() {
		return stock_batch_no;
	}

	public void setStock_batch_no(String stock_batch_no) {
		this.stock_batch_no = stock_batch_no;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public BigDecimal getGood_price() {
		return good_price;
	}

	public void setGood_price(BigDecimal good_price) {
		this.good_price = good_price;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getTotal_price() {
		return total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
}

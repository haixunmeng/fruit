package fruit.market.data;

import java.math.BigDecimal;

public class StockInDetail {

	private String stock_in_detail_id;

	private String stock_in_batch_no;

	private String good_id;

	private String value_unit;
	
	private BigDecimal in_num;

	private BigDecimal in_price;

	private BigDecimal total_price;

	private String remark;

	public String getStock_in_detail_id() {
		return stock_in_detail_id;
	}

	public void setStock_in_detail_id(String stock_in_detail_id) {
		this.stock_in_detail_id = stock_in_detail_id;
	}

	public String getStock_in_batch_no() {
		return stock_in_batch_no;
	}

	public void setStock_in_batch_no(String stock_in_batch_no) {
		this.stock_in_batch_no = stock_in_batch_no;
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

	public BigDecimal getIn_num() {
		return in_num;
	}

	public void setIn_num(BigDecimal in_num) {
		this.in_num = in_num;
	}

	public BigDecimal getIn_price() {
		return in_price;
	}

	public void setIn_price(BigDecimal in_price) {
		this.in_price = in_price;
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
}

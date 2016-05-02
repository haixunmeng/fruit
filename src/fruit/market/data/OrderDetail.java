package fruit.market.data;

import java.math.BigDecimal;

public class OrderDetail {

	private String order_detail_id;
	
	private String order_id;

	private String good_id;
	
	private String value_unit;

	private BigDecimal final_price;
	
	private BigDecimal num;

	private BigDecimal total_price;

	private String remark;

	
	
	public String getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(String order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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

	public BigDecimal getFinal_price() {
		return final_price;
	}

	public void setFinal_price(BigDecimal final_price) {
		this.final_price = final_price;
	}
	
	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
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

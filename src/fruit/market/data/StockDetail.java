package fruit.market.data;

import java.math.BigDecimal;

public class StockDetail {

	private String stock_detail_id;

	private String stock_batch_no;

	private String good_id;

	private String good_name;

	private String value_unit;

	private BigDecimal in_price;

	private String package_type;

	private BigDecimal package_weight;

	private BigDecimal package_deposit;

	private Integer package_num;

	private BigDecimal gross_weight;

	private BigDecimal net_weight;

	private BigDecimal total_deposit;

	private BigDecimal total_goods_price;

	private BigDecimal total_price;

	private String remark;

	public String getStock_detail_id() {
		return stock_detail_id;
	}

	public void setStock_detail_id(String stock_detail_id) {
		this.stock_detail_id = stock_detail_id;
	}

	public String getStock_batch_no() {
		return stock_batch_no;
	}

	public void setStock_batch_no(String stock_batch_no) {
		this.stock_batch_no = stock_batch_no;
	}

	public String getGood_id() {
		return good_id;
	}

	public void setGood_id(String good_id) {
		this.good_id = good_id;
	}

	public String getGood_name() {
		return good_name;
	}

	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}

	public String getValue_unit() {
		return value_unit;
	}

	public void setValue_unit(String value_unit) {
		this.value_unit = value_unit;
	}

	public BigDecimal getIn_price() {
		return in_price;
	}

	public void setIn_price(BigDecimal in_price) {
		this.in_price = in_price;
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

	public Integer getPackage_num() {
		return package_num;
	}

	public void setPackage_num(Integer package_num) {
		this.package_num = package_num;
	}

	public BigDecimal getGross_weight() {
		return gross_weight;
	}

	public void setGross_weight(BigDecimal gross_weight) {
		this.gross_weight = gross_weight;
	}

	public BigDecimal getNet_weight() {
		return net_weight;
	}

	public void setNet_weight(BigDecimal net_weight) {
		this.net_weight = net_weight;
	}

	public BigDecimal getTotal_deposit() {
		return total_deposit;
	}

	public void setTotal_deposit(BigDecimal total_deposit) {
		this.total_deposit = total_deposit;
	}

	public BigDecimal getTotal_goods_price() {
		return total_goods_price;
	}

	public void setTotal_goods_price(BigDecimal total_goods_price) {
		this.total_goods_price = total_goods_price;
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

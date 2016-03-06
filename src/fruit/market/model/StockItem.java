package fruit.market.model;

import java.math.BigDecimal;

public class StockItem {

	// 进货项id
	private String id;
	// 进货批次id
	private String stock_id;
	// 水果id
	private Integer fruit_id;
	// 包装方式
	private char package_type;
	// 包装数量
	private Integer package_num;
	// 价格
	private BigDecimal price;
	// 净重
	private double net_weight;
	// 包装重量
	private double package_weight;
	// 总重量
	private double gross_weight;
	// 总价
	private BigDecimal total_price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStock_id() {
		return stock_id;
	}

	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}

	public int getFruit_id() {
		return fruit_id;
	}

	public void setFruit_id(int fruit_id) {
		this.fruit_id = fruit_id;
	}

	public char getPackage_type() {
		return package_type;
	}

	public void setPackage_type(char package_type) {
		this.package_type = package_type;
	}

	public int getPackage_num() {
		return package_num;
	}

	public void setPackage_num(int package_num) {
		this.package_num = package_num;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public double getNet_weight() {
		return net_weight;
	}

	public void setNet_weight(double net_weight) {
		this.net_weight = net_weight;
	}

	public double getPackage_weight() {
		return package_weight;
	}

	public void setPackage_weight(double package_weight) {
		this.package_weight = package_weight;
	}

	public double getGross_weight() {
		return gross_weight;
	}

	public void setGross_weight(double gross_weight) {
		this.gross_weight = gross_weight;
	}

	public BigDecimal getTotal_price() {
		return total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}
}

package fruit.market.model;

import java.math.BigDecimal;

public class OrderItem {
	//订单项id
	private String id;
	//订单id
	private String OrderId;
	//水果id
	private Integer fruitId;
	//单价
	private BigDecimal price;
	//包装类型
	private char package_type;
	//包装数
	private Integer package_num;
	//单个包装重量
	private double single_pacakge_weight;
	//净重
	private double net_weight;
	//包装总重
	private double package_weight;
	//总重
	private double total_weight;
	//总价
	private BigDecimal total_price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public Integer getFruitId() {
		return fruitId;
	}

	public void setFruitId(Integer fruitId) {
		this.fruitId = fruitId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public char getPackage_type() {
		return package_type;
	}

	public void setPackage_type(char package_type) {
		this.package_type = package_type;
	}

	public Integer getPackage_num() {
		return package_num;
	}

	public void setPackage_num(Integer package_num) {
		this.package_num = package_num;
	}

	public double getSingle_pacakge_weight() {
		return single_pacakge_weight;
	}

	public void setSingle_pacakge_weight(double single_pacakge_weight) {
		this.single_pacakge_weight = single_pacakge_weight;
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

	public double getTotal_weight() {
		return total_weight;
	}

	public void setTotal_weight(double total_weight) {
		this.total_weight = total_weight;
	}

	public BigDecimal getTotal_price() {
		return total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}
}

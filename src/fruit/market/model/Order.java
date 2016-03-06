package fruit.market.model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
	// 订单id
	private String id;
	// 卖家id
	private String saler;
	// 买家id
	private String buyer;
	// 总价
	private BigDecimal total_price;
	// 创建时间
	private Date create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public BigDecimal getTotal_price() {
		return total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}

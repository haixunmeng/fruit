package fruit.market.model;

import java.sql.Date;

public class User {
	private String id;
	private String login_name;
	private String password;
	private String name;
	private String type;
	private Date create_time;
	private String phone;
	private String car_num;
	private String own_shop_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCar_num() {
		return car_num;
	}

	public void setCar_num(String car_num) {
		this.car_num = car_num;
	}

	public String getOwn_shop_name() {
		return own_shop_name;
	}

	public void setOwn_shop_name(String own_shop_name) {
		this.own_shop_name = own_shop_name;
	}

}

package fruit.market.model;

import java.util.Date;

public class Fruit {
	// 水果id
	private Integer id;
	// 名字
	private String name;
	// 图片链接
	private String url;
	// 创建时间
	private Date create_time;
	// 操作者
	private String operator;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}

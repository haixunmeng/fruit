package fruit.market.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fruit.market.data.OrderDetail;

@Repository
public interface OrderDetailDao extends BaseDao<OrderDetail>{

	public List<OrderDetail> getOrderDetail(String order_id);
	
}

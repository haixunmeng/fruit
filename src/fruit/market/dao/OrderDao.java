package fruit.market.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fruit.market.data.Order;

@Repository
public interface OrderDao extends BaseDao<Order>{
	
	public List<Order> getUnproccessedOrder(Map<String, Object> conditions, int pageNum, int pageCount);

}

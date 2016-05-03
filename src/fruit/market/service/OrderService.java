package fruit.market.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {

	public void createOrder(Map<String, Object> params);
	
	public void createSellerOrder(Map<String, Object> params);
	
	public void createBuyerOrder(Map<String, Object> params);
	
	public void createBuyerBatchOrder(Map<String, Object> params);
	
	public void createSellerBatchOrder(Map<String, Object> params);

	public List<Map<String, Object>> getBuyerUnproccessedOrders(Map<String, Object> params);

	public List<Map<String, Object>> getSellerUnproccessedOrders(Map<String, Object> params);

	public Map<String, Object> getOrderDetail(Map<String, Object> params);

	public String confirmOrder(Map<String, Object> params);

	public String cancelOrder(Map<String, Object> params);

	public List<Map<String, Object>> getHistoryOrder(Map<String, Object> params);

	
}

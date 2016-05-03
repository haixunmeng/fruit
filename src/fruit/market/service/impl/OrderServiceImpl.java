package fruit.market.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.cache.CacheManager;
import fruit.market.dao.GoodDao;
import fruit.market.dao.OrderDao;
import fruit.market.dao.OrderDetailDao;
import fruit.market.dao.SellingDao;
import fruit.market.dao.StockDao;
import fruit.market.dao.StoreDao;
import fruit.market.dao.UserDao;
import fruit.market.data.Good;
import fruit.market.data.Order;
import fruit.market.data.OrderDetail;
import fruit.market.data.OrderStatus;
import fruit.market.data.Role;
import fruit.market.data.Selling;
import fruit.market.data.Stock;
import fruit.market.data.Store;
import fruit.market.data.User;
import fruit.market.exception.FruitException;
import fruit.market.service.OrderService;
import fruit.market.utils.DateUtil;
import fruit.market.utils.Utils;

@Service
public class OrderServiceImpl implements OrderService{

	private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SellingDao sellingDao;
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Override
	public void createOrder(Map<String, Object> params) {
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		Order order = new Order();
		order.setOrder_id(Utils.get_uuid());
		order.setRemark(String.valueOf(params.get("remark")));
		order.setCreate_time(DateUtil.getTimestamp());
		order.setStore_id((String)params.get("store"));
		order.setBuyer_id((String)params.get("buyer"));
		order.setOrder_status((String)params.get("order_status"));

		List<Map<String, String>> goods = (List<Map<String, String>>) params.get("goods");
		
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		List<Stock> updateStocks = new ArrayList<Stock>();
		BigDecimal orderPrice = new BigDecimal("0");
		
		for(int i=0;i<goods.size();i++){
			OrderDetail orderDetail = new OrderDetail();
			
			Map<String, String> good = goods.get(i);
			
			orderDetail.setOrder_detail_id(Utils.get_uuid());
			orderDetail.setOrder_id(order.getOrder_id());
			orderDetail.setGood_id(good.get("good_id"));
			
			conditions.clear();
			conditions.put("good_id", good.get("good_id"));
			List<Selling> sellingGoods = sellingDao.getByCondition(conditions);
			
			if(sellingGoods.size() == 0){
				
			}
			
			Selling selling = sellingGoods.get(0);
			
			orderDetail.setValue_unit(selling.getValue_unit());
			BigDecimal sale_price = selling.getSale_price();
			orderDetail.setFinal_price(sale_price);
			BigDecimal buy_num = new BigDecimal((String)good.get("buy_num"));
			orderDetail.setNum(buy_num);
			
			Stock stock = stockDao.getSingleByCondition(conditions);
			if(buy_num.compareTo(stock.getLeft_num()) == 1){
				logger.info(FruitException.STOCK_NOT_ENOUGH_EXCEPTION);
				throw FruitException.STOCK_NOT_ENOUGH_EXCEPTION;
			}
			stock.setLeft_num(stock.getLeft_num().subtract(buy_num));
			updateStocks.add(stock);
			
			orderDetail.setTotal_price(sale_price.multiply(buy_num));
			orderDetail.setRemark(good.get("remark"));

			orderDetails.add(orderDetail);
			orderPrice = orderPrice.add(orderDetail.getTotal_price());
		}
		
		order.setOrder_price(orderPrice);
		orderDao.add(order);
		orderDetailDao.batchAdd(orderDetails);
		stockDao.batchUpdate(updateStocks);
	}

	@Override
	public void createSellerOrder(Map<String, Object> params) {
		
		User user = CacheManager.get((String)params.get("token"), User.class);
		if(null == user){
			logger.info(FruitException.CACHE_USER_IS_NULL_EXCEPTION);
			throw FruitException.CACHE_USER_IS_NULL_EXCEPTION;
		}
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		conditions.put("seller_id", user.getUser_id());
		Store store = storeDao.getSingleByCondition(conditions);
		
		params.put("store", store.getStore_id());
		params.put("order_status", OrderStatus.WAITTING_FOR_BUYER_CONFIRM);
	
		createOrder(params);
	}

	@Override
	public void createBuyerOrder(Map<String, Object> params) {
		
		User user = CacheManager.get((String)params.get("token"), User.class);
		if(null == user){
			logger.info(FruitException.CACHE_USER_IS_NULL_EXCEPTION);
			throw FruitException.CACHE_USER_IS_NULL_EXCEPTION;
		}
		
		params.put("buyer", user.getUser_id());
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		conditions.put("good_id", params.get("good_id"));
		Selling selling = sellingDao.getSingleByCondition(conditions);
		
		params.put("store", selling.getStore_id());
		
		List<Map<String, String>> goods = new ArrayList<Map<String, String>>();
		Map<String, String> good = new HashMap<String, String>();
		good.put("good_id", (String)params.get("good_id"));
		good.put("buy_num", (String)params.get("buy_num"));
		params.remove("good_id");
		params.remove("buy_num");
		goods.add(good);
		params.put("goods", goods);
		params.put("order_status", OrderStatus.WAITTING_FOR_SELLER_CONFIRM);
		
		createOrder(params);
	}

	@Override
	public List<Map<String, Object>> getBuyerUnproccessedOrders(Map<String, Object> params) {
		
		User user = CacheManager.get((String)params.get("token"), User.class);
		if(null == user){
			logger.info(FruitException.CACHE_USER_IS_NULL_EXCEPTION);
			throw FruitException.CACHE_USER_IS_NULL_EXCEPTION;
		}
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		conditions.put("buyer_id", user.getUser_id());
		
		List<Order> orders = orderDao.getUnproccessedOrder(conditions, Integer.valueOf(String.valueOf(params.get("pageNum"))), Integer.valueOf(String.valueOf(params.get("pageCount"))));
		
		List<Map<String, Object>> ordersMap = new ArrayList<Map<String, Object>>();
		if(orders.size() > 0){
			for(Order order : orders){
				Map<String, Object> orderMap = new HashMap<String, Object>();
				
				Store store = storeDao.getData(order.getStore_id());
				
				orderMap.put("order_id", order.getOrder_id());
				orderMap.put("store_name", store.getStore_name());
				orderMap.put("order_price", order.getOrder_price());
				orderMap.put("order_status", order.getOrder_status());
				orderMap.put("order_time", DateUtil.formatDate(order.getCreate_time()));
				
				ordersMap.add(orderMap);
			}
		}
		
		return ordersMap;
	}

	@Override
	public List<Map<String, Object>> getSellerUnproccessedOrders(Map<String, Object> params) {
		User user = CacheManager.get((String)params.get("token"), User.class);
		if(null == user){
			logger.info(FruitException.CACHE_USER_IS_NULL_EXCEPTION);
			throw FruitException.CACHE_USER_IS_NULL_EXCEPTION;
		}
		
		Store store = storeDao.getUserStore(user.getUser_id());
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		conditions.put("store_id", store.getStore_id());
		
		List<Order> orders = orderDao.getUnproccessedOrder(conditions, Integer.valueOf(String.valueOf(params.get("pageNum"))), Integer.valueOf(String.valueOf(params.get("pageCount"))));
		
		List<Map<String, Object>> ordersMap = new ArrayList<Map<String, Object>>();
		if(orders.size() > 0){
			for(Order order : orders){
				Map<String, Object> orderMap = new HashMap<String, Object>();
				
				orderMap.put("order_id", order.getOrder_id());
				orderMap.put("store_name", store.getStore_name());
				orderMap.put("order_price", order.getOrder_price());
				orderMap.put("order_status", order.getOrder_status());
				orderMap.put("order_time", DateUtil.formatDate(order.getCreate_time()));
				
				ordersMap.add(orderMap);
			}
		}
		
		return ordersMap;
	}

	@Override
	public Map<String, Object> getOrderDetail(Map<String, Object> params) {
		
		User user = CacheManager.get((String)params.get("token"), User.class);
		if(null == user){
			logger.info(FruitException.CACHE_USER_IS_NULL_EXCEPTION);
			throw FruitException.CACHE_USER_IS_NULL_EXCEPTION;
		}

		Order order = orderDao.getData((String)params.get("order_id"));
		
		Map<String, Object> orderDetail = new HashMap<String, Object>();
		
		orderDetail.put("order_id", order.getOrder_id());
		orderDetail.put("order_price", order.getOrder_price());
		orderDetail.put("order_status", order.getOrder_status());
		orderDetail.put("order_time", DateUtil.formatDate(order.getCreate_time()));
		orderDetail.put("user_type", user.getUser_type());
		
		Store store = storeDao.getData(order.getStore_id());
		orderDetail.put("store_name", store.getStore_name());
		
		List<OrderDetail> orderDetailList = orderDetailDao.getOrderDetail(order.getOrder_id());
		
		List<Map<String, Object>> goods = new ArrayList<Map<String, Object>>();
		
		for(OrderDetail oneOrderDetail : orderDetailList){
			Map<String, Object> oneOrderDetailMap = new HashMap<String, Object>();
			
			oneOrderDetailMap.put("good_id", oneOrderDetail.getGood_id());
			
			Good good = goodDao.getData(oneOrderDetail.getGood_id());
			oneOrderDetailMap.put("good_name", good.getGood_name());
			oneOrderDetailMap.put("good_photo_url", good.getGood_photo_url());
			
			oneOrderDetailMap.put("sale_price", oneOrderDetail.getFinal_price());
			oneOrderDetailMap.put("buy_num", oneOrderDetail.getNum());
			oneOrderDetailMap.put("value_unit", oneOrderDetail.getValue_unit());
			oneOrderDetailMap.put("total_price", oneOrderDetail.getTotal_price());
			
			goods.add(oneOrderDetailMap);
		}
		
		orderDetail.put("goods", goods);
		
		return orderDetail;
	}

	@Override
	public String confirmOrder(Map<String, Object> params) {
		
		String order_id = (String) params.get("order_id");
		
		Order order = orderDao.getData(order_id);
		
		order.setOrder_status(OrderStatus.FINISHED);
		
		orderDao.update(order);
		
		User user = CacheManager.get((String)params.get("token"), User.class);
		
		return user.getUser_type();
	}

	@Override
	public String cancelOrder(Map<String, Object> params) {
		String order_id = (String) params.get("order_id");
		
		Order order = orderDao.getData(order_id);
		
		order.setOrder_status(OrderStatus.CANCELED);
		
		orderDao.update(order);
		
		List<OrderDetail> goods = orderDetailDao.getOrderDetail(order_id);
		List<Stock> updateStocks = new ArrayList<Stock>();
		
		for(OrderDetail orderDetail : goods){
			Stock stock = stockDao.getGoodStock(orderDetail.getGood_id());
			
			stock.setLeft_num(stock.getLeft_num().add(orderDetail.getNum()));
			updateStocks.add(stock);
		}
		
		stockDao.batchUpdate(updateStocks);
		
		User user = CacheManager.get((String)params.get("token"), User.class);
		
		return user.getUser_type();
	}

	@Override
	public List<Map<String, Object>> getHistoryOrder(Map<String, Object> params) {
		
		User user = CacheManager.get((String)params.get("token"), User.class);
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		if(Role.BUYER.equals(user.getUser_type())){
			conditions.put("buyer_id", user.getUser_id());
		}else if(Role.SELLER.equals(user.getUser_type())){
			Store store = storeDao.getUserStore(user.getUser_id());
			conditions.put("store_id", store.getStore_id());
		}
		
		List<Order> orders = orderDao.getHistoryOrder(conditions, Integer.valueOf(String.valueOf(params.get("pageNum"))), Integer.valueOf(String.valueOf(params.get("pageCount"))));
		
		List<Map<String, Object>> historyOrders = new ArrayList<Map<String, Object>>();
		for(Order order : orders){
			Map<String, Object> historyOrder = new HashMap<String, Object>();
			
			User buyer = userDao.getData(order.getBuyer_id());
			
			historyOrder.put("order_id", order.getOrder_id());
			historyOrder.put("buyer_name", buyer.getUser_name());
			
			Store sellerStore = storeDao.getData(order.getStore_id());
			historyOrder.put("store_name", sellerStore.getStore_name());
			historyOrder.put("order_status", order.getOrder_status());
			historyOrder.put("order_price", order.getOrder_price());
			historyOrder.put("order_time", DateUtil.formatDate(order.getUpdate_time()));
			
			historyOrders.add(historyOrder);
		}
		
		return historyOrders;
	}

	@Override
	public void createBuyerBatchOrder(Map<String, Object> params) {
		User user = CacheManager.get((String)params.get("token"), User.class);
		if(null == user){
			logger.info(FruitException.CACHE_USER_IS_NULL_EXCEPTION);
			throw FruitException.CACHE_USER_IS_NULL_EXCEPTION;
		}
		
		params.put("buyer", user.getUser_id());
		params.put("order_status", OrderStatus.WAITTING_FOR_SELLER_CONFIRM);
		
		createOrder(params);
		
	}
}
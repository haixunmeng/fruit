package fruit.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fruit.market.exception.FruitException;
import fruit.market.service.OrderService;

@Controller
@RequestMapping("/order")
public class fruit_order_controller {
	
	private static Logger logger = Logger.getLogger(fruit_order_controller.class);

	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/createSellerOrder")
	@ResponseBody
	public Map<String, Object> createOrder(@RequestBody Map<String, Object> params){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			logger.info(params);
			
			orderService.createSellerOrder(params);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/createBuyerOrder")
	@ResponseBody
	public Map<String, Object> createBuyerOrder(@RequestBody Map<String, Object> params){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			logger.info(params);
			
			orderService.createBuyerOrder(params);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/getBuyerUnproccessedOrders")
	@ResponseBody
	public Map<String, Object> getBuyerUnproccessedOrders(@RequestBody Map<String, Object> params){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			logger.info(params);
			
			List<Map<String, Object>> orders = orderService.getBuyerUnproccessedOrders(params);
			
			
			resMeg.put("data", orders);
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	@RequestMapping("/getSellerUnproccessedOrders")
	@ResponseBody
	public Map<String, Object> getSellerUnproccessedOrders(@RequestBody Map<String, Object> params){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			logger.info(params);
			
			List<Map<String, Object>> orders =  orderService.getSellerUnproccessedOrders(params);
			
			resMeg.put("data", orders);
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	
	@RequestMapping("/getOrderDetail")
	@ResponseBody
	public Map<String, Object> getOrderDetail(@RequestBody Map<String, Object> params){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			logger.info(params);
			
			Map<String, Object> orderDetail = orderService.getOrderDetail(params);
			
			resMeg.put("data", orderDetail);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/confirmOrder")
	@ResponseBody
	public Map<String, Object> confirmOrder(@RequestBody Map<String, Object> params){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			logger.info(params);
			
			String user_type = orderService.confirmOrder(params);
			
			resMeg.put("user_type", user_type);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/cancelOrder")
	@ResponseBody
	public Map<String, Object> cancelOrder(@RequestBody Map<String, Object> params){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			logger.info(params);
			
			String user_type = orderService.cancelOrder(params);
			
			resMeg.put("user_type", user_type);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
}

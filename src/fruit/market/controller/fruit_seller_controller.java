package fruit.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fruit.market.data.Role;
import fruit.market.exception.FruitException;
import fruit.market.service.OrderService;
import fruit.market.service.SellerService;
import fruit.market.service.StockService;
import fruit.market.service.UserService;

@Controller
@RequestMapping("/seller")
public class fruit_seller_controller {
	
	private static Logger logger = Logger.getLogger(fruit_seller_controller.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private SellerService sellerService;
	
	@RequestMapping("/getAllSeller")
	@ResponseBody
	public Map<String, Object> getAllSeller() {

		Map<String, Object> resMeg = new HashMap<String, Object>();

		try {
			
			Map<String, String> params = new HashMap<String, String>(1);
			
			params.put("user_type", Role.SELLER);
			
			List<Map<String, String>> sellers =  userService.getAllSeller(params);
			
			resMeg.put("sellers", sellers);

			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/getStock")
	@ResponseBody
	public Map<String, Object> getStock(@RequestBody Map<String, String> params){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			logger.info(params);
			
			List<Map<String, Object>> stock = stockService.getStock(params);
			
			resMeg.put("data", stock);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/shelfGood")
	@ResponseBody
	public Map<String, Object> shelfGood(HttpServletRequest request){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			sellerService.shelfGood(request);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/createBatchOrder")
	@ResponseBody
	public Map<String, Object> createBatchOrder(@RequestBody Map<String, Object> params){

		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try{
			
			logger.info(params);
			
			orderService.createSellerBatchOrder(params);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		}catch(FruitException e){
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		}
		
		return resMeg;
	}
}

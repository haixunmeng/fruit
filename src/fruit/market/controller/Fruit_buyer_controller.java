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
import fruit.market.service.UserService;

@Controller
@RequestMapping("/buyer")
public class Fruit_buyer_controller {
	
	private static Logger logger = Logger.getLogger(Fruit_buyer_controller.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;

	@RequestMapping("/createBatchOrder")
	@ResponseBody
	public Map<String, Object> createBatchOrder(@RequestBody Map<String, Object> params){

		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try{
			
			orderService.createBuyerBatchOrder(params);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		}catch(FruitException e){
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		}
		
		return resMeg;
	}
	
	@RequestMapping("/getAllBuyer")
	@ResponseBody
	public Map<String, Object> getAllbuyer(){
		
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try{
			
			List<Map<String, String>> buyers = userService.getAllBuyer();
			
			resMeg.put("data", buyers);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		}catch(FruitException e){
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		}
		
		return resMeg;
	}
	
}

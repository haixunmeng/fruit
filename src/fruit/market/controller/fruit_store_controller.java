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

import fruit.market.data.Selling;
import fruit.market.data.Store;
import fruit.market.exception.FruitException;
import fruit.market.service.StoreService;

@Controller
@RequestMapping("/store")
public class fruit_store_controller {

	private static Logger logger = Logger.getLogger(fruit_store_controller.class);

	@Autowired
	private StoreService storeService;
	
	@RequestMapping("/getAllStores")
	@ResponseBody
	public Map<String, Object> getAllStores(){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			List<Store> users = storeService.getAllStore();
			
			resMeg.put("data", users);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/getAllStoresGoods")
	@ResponseBody
	public Map<String, Object> getAllStoresGoods(@RequestBody Map<String, Object> params){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			logger.info(params);
			
			List<Map<String, String>> goods = storeService.getAllStoresGoods(params);
			
			resMeg.put("data", goods);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
}

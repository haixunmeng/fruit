package fruit.market.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fruit.market.exception.FruitException;
import fruit.market.service.StoreService;
import fruit.market.service.UserService;
import fruit.market.utils.Utils;

@Controller
@RequestMapping("/manager")
public class fruit_manager_controller {
	
	private static Logger logger = Logger.getLogger(fruit_manager_controller.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private StoreService storeService;
	
	
	
	@RequestMapping("/addSeller")
	@ResponseBody
	public Map<String, String> addSeller(@RequestBody Map<String, String> params){
		Map<String, String> resMeg = new HashMap<String, String>();

		try {
			
			logger.info(params);
			
			userService.addSeller(params);

			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/addStore")
	@ResponseBody
	public Map<String, String> addStore(@RequestBody Map<String, String> params){
		Map<String, String> resMeg = new HashMap<String, String>();
		
		try {
			
			logger.info(params);
			
			storeService.addStore(params);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
}

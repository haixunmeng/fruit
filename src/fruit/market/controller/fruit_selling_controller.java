package fruit.market.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fruit.market.exception.FruitException;
import fruit.market.service.SellingService;

@Controller
@RequestMapping("/selling")
public class fruit_selling_controller {
	
	private static Logger logger = Logger.getLogger(fruit_selling_controller.class);

	@Autowired
	private SellingService sellingService;

	@RequestMapping("/unshelfGood")
	@ResponseBody
	public Map<String, String> unshelfGood(@RequestBody Map<String, String> params) {

		Map<String, String> resMeg = new HashMap<String, String>();

		try {
			
			logger.info(params);
			
			sellingService.unshelfGood(params);

			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
	
	@RequestMapping("/updateSalePrice")
	@ResponseBody
	public Map<String, String> updateSalePrice(@RequestBody Map<String, String> params) {
		
		Map<String, String> resMeg = new HashMap<String, String>();
		
		try {
			
			logger.info(params);
			
			sellingService.updateSalePrice(params);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} 
		
		return resMeg;
	}
}

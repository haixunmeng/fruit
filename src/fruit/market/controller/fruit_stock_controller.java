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
import fruit.market.service.StockInService;

@Controller
@RequestMapping("/stock")
public class fruit_stock_controller {
	
	private static Logger logger = Logger.getLogger(fruit_stock_controller.class);
	
	@Autowired
	private StockInService stockService;
	
	@RequestMapping("/stockIn")
	@ResponseBody
	public Map<String, Object> stockIn(@RequestBody Map<String, Object> params){

		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try{
			logger.info(params);
			
			stockService.StockIn(params);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		}catch(FruitException e){
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		}
		
		return resMeg;
	}
}

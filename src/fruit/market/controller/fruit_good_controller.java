package fruit.market.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fruit.market.exception.FruitException;
import fruit.market.service.GoodService;

@Controller
@RequestMapping("/good")
public class fruit_good_controller {
	
	@Autowired
	private GoodService goodService;
	
	@RequestMapping("/loadGood")
	@ResponseBody
	public Map<String, Object> loadGood(@RequestBody Map<String, String> params){

		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try{
			
			resMeg.put("data", goodService.loadSellingGoods(params));
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		}catch(FruitException e){
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		}
		
		return resMeg;
	}
	
	@RequestMapping("/loadGoodInfo")
	@ResponseBody
	public Map<String, Object> loadGoodInfo(@RequestBody Map<String, String> params){
		
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try{
			
			resMeg.put("data", goodService.loadGoodInfo(params));
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		}catch(FruitException e){
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		}
		
		return resMeg;
	}

}

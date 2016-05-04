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
import fruit.market.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class Fruit_notice_controller {
	
	private static Logger logger = Logger.getLogger(Fruit_notice_controller.class);
	
	@Autowired
	private NoticeService noticeService;

	@RequestMapping("/addNotice")
	@ResponseBody
	public Map<String, Object> addNotice(@RequestBody Map<String, String> params){

		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try{
			
			logger.info(params);
			
			noticeService.addNotice(params);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		}catch(FruitException e){
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		}
		
		return resMeg;
	}
	
	@RequestMapping("/getLatestNotice")
	@ResponseBody
	public Map<String, Object> getLatestNotice(){

		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try{
			
			String notice = noticeService.getLatestNotice();
			
			resMeg.put("data", notice);
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		}catch(FruitException e){
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		}
		
		return resMeg;
	}
}

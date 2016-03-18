package fruit.market.controller;

import java.util.HashMap;
import java.util.List;
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
import fruit.market.service.UserService;
import fruit.market.session.SessionManager;
import fruit.market.utils.Utils;

@Controller
@RequestMapping("/seller")
public class fruit_seller_controller {
	
	private static Logger logger = Logger.getLogger(fruit_seller_controller.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/getAllSeller")
	@ResponseBody
	public Map<String, Object> getAllSeller(@RequestBody Map<String, String> params) {

		Map<String, Object> resMeg = new HashMap<String, Object>();

		try {
			
			logger.info(params);
			
			params.put("user_type", "S");
			
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
}

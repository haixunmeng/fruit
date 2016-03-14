package fruit.market.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fruit.market.exception.FruitException;
import fruit.market.service.UserService;
import fruit.market.utils.Utils;

@Controller
@RequestMapping("/manager")
public class fruit_manager_controller {
	
	private static Logger logger = Logger.getLogger(fruit_manager_controller.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping("/addSeller")
	public void addSeller(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> resMeg = new HashMap<String, Object>();

		try {
			
			Map<String, Object> params = Utils.readParameters(request);
			
			logger.info(params);
			
			userService.addSeller(params);

			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} finally{
			Utils.writeMessage(response, resMeg);
		}
	}
	
}

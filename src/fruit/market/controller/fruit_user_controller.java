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
import fruit.market.session.SessionManager;
import fruit.market.utils.Utils;

@Controller
@RequestMapping("/user")
public class fruit_user_controller {
	
	private static Logger logger = Logger.getLogger(fruit_user_controller.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> resMeg = new HashMap<String, Object>();

		try {
			
			Map<String, Object> params = Utils.readParameters(request);
			
			logger.info(params);
			
			userService.register(params);

			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} finally{
			Utils.writeMessage(response, resMeg);
		}
	}
	
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			Map<String, Object> params = Utils.readParameters(request);
			
			logger.info(params);
			
			userService.login(params);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} finally{
			Utils.writeMessage(response, resMeg);
		}
	}
	
	@RequestMapping("/getPassCode")
	public void getPassCode(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			Map<String, Object> params = Utils.readParameters(request);
			
			logger.info(params);
			
			String passCode = Utils.getPassCode();

			String sessionId = Utils.get_uuid();
			
			SessionManager.save2session(sessionId, "passCode", passCode.split(":")[0]);
			
			resMeg.put("sessionId", sessionId);
			resMeg.put("passCode", passCode.split(":")[1]);
			
			resMeg.put("code", FruitException.OPTIONS_SUCCESS.errorCode);
			resMeg.put("msg", FruitException.OPTIONS_SUCCESS.errorMsg);
			
		} catch (FruitException e) {
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
		} finally{
			Utils.writeMessage(response, resMeg);
		}
	}
	
	@RequestMapping("/checkPassCode")
	public void checkPassCode(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> resMeg = new HashMap<String, Object>();
		
		try {
			
			Map<String, Object> params = Utils.readParameters(request);
			
			logger.info(params);
			
			Utils.checkPassCode(params);
			
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

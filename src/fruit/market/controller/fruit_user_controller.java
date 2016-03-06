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

import fruit.market.exception.DaoException;
import fruit.market.exception.FruitException;
import fruit.market.exception.ServiceException;
import fruit.market.model.User;
import fruit.market.service.UserService;
import fruit.market.utils.ErrorMeg;
import fruit.market.utils.ResponseMeg;
import fruit.market.utils.Utils;

@Controller
@RequestMapping("/user")
public class fruit_user_controller {
	
	private static Logger logger = Logger.getLogger(fruit_user_controller.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/register")
	@ResponseBody
	public void addUser(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> resMeg = new HashMap<String, Object>();

		try {
			
			Map<String, Object> params = Utils.readParameters(request);
			
			logger.info(params);
			
			userService.register(params);

			resMeg.put("res_code", FruitException.OPTIONS_SUCCESS.error_code);
			resMeg.put("res_msg", FruitException.OPTIONS_SUCCESS.error_msg);
			
		} catch (FruitException e) {
			resMeg.put("res_code", e.error_code);
			resMeg.put("res_msg", e.error_msg);
		} finally{
			Utils.writeMessage(response, resMeg);
		}

	}

	
	@RequestMapping("/updateUser")
	@ResponseBody
	public ResponseMeg updateUser(@RequestBody User user) {
		ResponseMeg resMeg = new ResponseMeg();

		try {
		    userService.updateUser(user);
		    
			resMeg.errorCode = ErrorMeg.CALL_SUCCESS.errorCode;
			resMeg.meg = ErrorMeg.CALL_SUCCESS.errorMeg;
		} catch (ServiceException e) {
			resMeg.errorCode = e.getErrorMeg().errorCode;
			resMeg.meg = e.getErrorMeg().errorMeg;
		} catch (DaoException e) {
			resMeg.errorCode = e.getErrorMeg().errorCode;
			resMeg.meg = e.getErrorMeg().errorMeg;
		}

		return resMeg;
	}
	
}

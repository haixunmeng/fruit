package fruit.market.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.controller.fruit_user_controller;
import fruit.market.dao.UserDao;
import fruit.market.exception.FruitException;
import fruit.market.model.User;
import fruit.market.service.UserService;
import fruit.market.session.SessionManager;
import fruit.market.utils.Utils;


@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = Logger.getLogger(fruit_user_controller.class);
	
	@Autowired
	private UserDao userDao;

	public void register(Map<String, Object> params) {
		
		String passed = SessionManager.get4session((String) params.get("sessionId"), "passed"); 
		
		if(!"true".equals(passed)){
			logger.info(FruitException.PASSCODE_ERROR_EXCEPTION);
			throw FruitException.PASSCODE_ERROR_EXCEPTION;
		}

		String phone = (String) params.get("phone");
		
		User user = userDao.queryByConditions("phone", phone);
		
		if(null != user){
			logger.info(phone + " " + FruitException.PHONE_HAS_BEEN_REGISTED_EXCEPTION);
			throw FruitException.PHONE_HAS_BEEN_REGISTED_EXCEPTION;
		}
		
		params.put("user_id", Utils.get_uuid());
		params.put("pwd", Utils.encrypt((String) params.get("pwd")));
		params.put("user_type", "C");
		params.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		userDao.register(params);
		
	}

	@Override
	public void login(Map<String, Object> params) {
		
		String passed = SessionManager.get4session((String) params.get("sessionId"), "passed"); 
		
		if(!"true".equals(passed)){
			logger.info(FruitException.PASSCODE_ERROR_EXCEPTION);
			throw FruitException.PASSCODE_ERROR_EXCEPTION;
		}
		
		User user = userDao.queryByConditions("phone", (String) params.get("phone"));
		
		if(null == user){
			logger.info(FruitException.USER_NOT_EXISTS_EXCEPTION);
			throw FruitException.USER_NOT_EXISTS_EXCEPTION;
		}
		
		if(!Utils.checkPwd(user.getPwd(), (String) params.get("pwd"))){
			logger.info(FruitException.PASSWORD_NOT_CORRECT_EXCEPTION);
			throw FruitException.PASSWORD_NOT_CORRECT_EXCEPTION;
		}
  		
	}

}

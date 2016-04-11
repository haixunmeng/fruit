package fruit.market.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.cache.CacheManager;
import fruit.market.controller.fruit_user_controller;
import fruit.market.dao.UserDao;
import fruit.market.data.Role;
import fruit.market.data.User;
import fruit.market.exception.FruitException;
import fruit.market.service.UserService;
import fruit.market.utils.Utils;


@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = Logger.getLogger(fruit_user_controller.class);
	
	@Autowired
	private UserDao userDao;

	@Override
	public void register(Map<String, String> params) {
		
		String passCodeCommited = (String) params.get("passCode");
		
		String localPassCode = CacheManager.hashget((String) params.get("sessionId"), "passCode", String.class);
		
		if(localPassCode == null){
			logger.info(FruitException.OPERATION_OUT_TIME);
			throw FruitException.OPERATION_OUT_TIME;
		}
		
		if(!localPassCode.equals(passCodeCommited)){
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
		params.put("user_type", Role.BUYER);
		params.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		userDao.insertUser(params);
		
	}

	@Override
	public void login(Map<String, String> params) {
		
		String passCodeCommited = params.get("passCode");
		
		String sessionId = params.get("sessionId");
		
		String localPassCode = CacheManager.hashget(sessionId, "passCode", String.class);
		
		if(localPassCode == null){
			logger.info(FruitException.OPERATION_OUT_TIME);
			throw FruitException.OPERATION_OUT_TIME;
		}
		
		if(!localPassCode.equals(passCodeCommited)){
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
		
		CacheManager.set(sessionId, user);
	}

	@Override
	public void addSeller(Map<String, String> params) {
		
		String phone = (String) params.get("phone");
		
		User user = userDao.queryByConditions("phone", phone);
		
		if(null != user){
			logger.info(phone + " " + FruitException.PHONE_HAS_BEEN_REGISTED_EXCEPTION);
			throw FruitException.PHONE_HAS_BEEN_REGISTED_EXCEPTION;
		}
		
		params.put("user_id", Utils.get_uuid());
		params.put("pwd", Utils.encrypt((String) params.get("pwd")));
		params.put("user_type", "S");
		params.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		userDao.insertUser(params);
		
	}

	@Override
	public List<Map<String, String>> getAllSeller(Map<String, String> params) {

		List<User> sellers = userDao.queryListByConditions(params);
		
		List<Map<String, String>> allSellers = new ArrayList<Map<String, String>>();
		
		for(User seller : sellers){
			Map<String, String> singleSeller = new HashMap<String, String>(2);
			
			singleSeller.put("userid", seller.getUser_id());
			singleSeller.put("username", seller.getUser_name());
			
			allSellers.add(singleSeller);
		}
		
		return allSellers;
	}

}

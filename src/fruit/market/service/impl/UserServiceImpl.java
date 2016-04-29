package fruit.market.service.impl;

import java.util.ArrayList;
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
import fruit.market.utils.DateUtil;
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
		
		User newUser = new User();
		
		newUser.setPhone(phone);
		newUser.setUser_id(Utils.get_uuid());
		newUser.setPwd(Utils.encrypt((String) params.get("pwd")));
		newUser.setUser_name(params.get("username"));
		newUser.setUser_type(Role.BUYER);
		newUser.setCreate_time(DateUtil.getDateString());
		
		userDao.add(newUser);
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
		
		User newUser = new User();
		
		newUser.setPhone(phone);
		newUser.setUser_id(Utils.get_uuid());
		newUser.setPwd(Utils.encrypt((String) params.get("pwd")));
		newUser.setUser_name(params.get("username"));
		newUser.setUser_type(Role.SELLER);
		newUser.setCreate_time(DateUtil.getDateString());
		
		userDao.add(newUser);
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

	@Override
	public void logout(Map<String, String> params) {

		User login_user = null;
		
		String token = params.get("token");

		if(null != token && !token.equals("")){
			login_user = CacheManager.get(token, User.class);
		}else{
			logger.info(FruitException.TOKEN_NULL_EXCEPTION);
			throw FruitException.TOKEN_NULL_EXCEPTION;
		}
		
		if(login_user != null){
			CacheManager.remove(token);
		}else{
			logger.info(FruitException.NO_LOGINED_USER_EXCEPTION);
			throw FruitException.NO_LOGINED_USER_EXCEPTION;
		}
		
		
	}

	@Override
	public List<User> getUsers(Map<String, String> params) {
		
		int pageNum = Integer.valueOf(params.get("pageNum"));
		int pageCount = Integer.valueOf(params.get("pageCount"));
		
		return userDao.getOnePage(pageNum, pageCount);
	}

}

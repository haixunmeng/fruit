package fruit.market.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.dao.UserDao;
import fruit.market.exception.ServiceException;
import fruit.market.model.User;
import fruit.market.service.UserService;
import fruit.market.utils.ErrorMeg;
import fruit.market.utils.Utils;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean addUser(User user) {
		
		user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		
		return userDao.addUser(user);
	}

	@Override
	public boolean deleteUser(Integer userId) {
		boolean deleteResult = userDao.deleteUser(userId);
		
		if(deleteResult == false){
			throw new ServiceException(ErrorMeg.DATA_NOT_EXIST);
		}
		
		return deleteResult;
	}

	@Override
	public boolean updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public User getUser(Integer userId) {
		return userDao.getUser(userId);
	}

	public void register(Map<String, Object> params) {

		params.put("user_id", Utils.get_uuid());
		
		params.put("user_type", "C");
		params.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		userDao.register(params);
		
	}

}

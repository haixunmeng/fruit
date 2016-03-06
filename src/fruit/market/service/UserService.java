package fruit.market.service;


import java.util.Map;

import org.springframework.stereotype.Service;

import fruit.market.model.User;


@Service
public interface UserService {
	
	public void register(Map<String, Object> parameters);

	public boolean addUser(User user);
	
	public boolean deleteUser(Integer userId);

	public boolean updateUser(User user);
	
	public User getUser(Integer userId);
}

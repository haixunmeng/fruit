package fruit.market.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import fruit.market.model.User;


@Repository
public interface UserDao {
	
	public boolean addUser(User user);
	
	public boolean deleteUser(Integer userId);

	public boolean updateUser(User user);
	
	public User getUser(Integer userId);

	public void register(Map<String, Object> parameters);
}

package fruit.market.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fruit.market.model.User;


@Repository
public interface UserDao {
	
	public void insertUser(Map<String, Object> parameters);

	public User queryByConditions(String...conditions);
	
	public List<User> queryListByConditions(Map<String, Object> conditions);
}

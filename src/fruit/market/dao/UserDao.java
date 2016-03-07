package fruit.market.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import fruit.market.model.User;


@Repository
public interface UserDao {
	
	public void register(Map<String, Object> parameters);

	public User queryByConditions(String...conditions);
}

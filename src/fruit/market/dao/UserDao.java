package fruit.market.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fruit.market.data.User;


@Repository
public interface UserDao extends BaseDao<User>{
	
	public User queryByConditions(String...conditions);
	
	public List<User> queryListByConditions(Map<String, String> conditions);
}

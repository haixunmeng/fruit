package fruit.market.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fruit.market.data.Resource;

@Repository
public interface AuthDao extends BaseDao<Resource>{
	
	public List<String> getAuthRole(String action_id);
	
}

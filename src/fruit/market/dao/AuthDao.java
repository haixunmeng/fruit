package fruit.market.dao;

import org.springframework.stereotype.Repository;

import fruit.market.data.Resource;

@Repository
public interface AuthDao{
	
	public Resource getData(String action);

}
//@Repository
//public interface AuthDao extends BaseDao<Resource>{
//	
//}

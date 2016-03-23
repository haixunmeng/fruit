package fruit.market.dao.impl;

import org.springframework.stereotype.Repository;

import fruit.market.dao.AuthDao;
import fruit.market.data.Resource;

@Repository
public class AuthDaoImpl implements AuthDao {

	@Override
	public Resource getData(String action) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
//@Repository
//public class AuthDaoImpl extends BaseDaoImpl<Resource> implements AuthDao {
//
//	public AuthDaoImpl(){
//		tableName = "resource_2_role";
//		primaryKey = "resource_id";
//	}
//	
//}

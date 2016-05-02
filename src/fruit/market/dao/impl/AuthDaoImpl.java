package fruit.market.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fruit.market.dao.AuthDao;
import fruit.market.data.Resource;
import fruit.market.exception.FruitException;

@Repository
public class AuthDaoImpl extends BaseDaoImpl<Resource> implements AuthDao {
	
	private static Logger logger = Logger.getLogger(AuthDaoImpl.class);

	public AuthDaoImpl(){
		tableName = "resources_2_role";
		primaryKey = "resource_id";
	}

	@Override
	public List<String> getAuthRole(String action_id) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select role from ").append(tableName).append(" where resource_id=?");
		
		try{
			return jdbcTemplate.queryForList(sql.toString(), new Object[]{action_id}, String.class);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
	
}

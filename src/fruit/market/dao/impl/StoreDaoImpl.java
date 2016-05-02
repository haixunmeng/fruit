package fruit.market.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fruit.market.dao.StoreDao;
import fruit.market.data.Store;
import fruit.market.exception.FruitException;

@Repository
public class StoreDaoImpl extends BaseDaoImpl<Store> implements StoreDao {
	
	private static Logger logger = Logger.getLogger(StoreDaoImpl.class);

	public StoreDaoImpl(){
		tableName = "fruit_store";
		primaryKey = "store_id";
	}

	@Override
	public Store getUserStore(String user_id) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from ").append(tableName).append(" where seller_id=?");
		
		try {
			return jdbcTemplate.queryForObject(sql.toString(), new Object[]{user_id}, rowMapper);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
		
}

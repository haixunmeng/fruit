package fruit.market.dao.impl;

import java.util.List;

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

	@Override
	public List<Store> getAllStore() {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from ").append(tableName).append(" where store_status='using'");
		
		try {
			return jdbcTemplate.query(sql.toString(), rowMapper);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public List<Store> loadStores(Integer pageNum, Integer pageCount) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from ").append(tableName).append(" limit ? offset ?");
		
		try {
			return jdbcTemplate.query(sql.toString(), new Object[] { pageCount, pageCount * (pageNum - 1) }, rowMapper);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
		
}

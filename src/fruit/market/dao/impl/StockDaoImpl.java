package fruit.market.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import fruit.market.dao.StockDao;
import fruit.market.data.Stock;
import fruit.market.exception.FruitException;

@Repository
public class StockDaoImpl extends BaseDaoImpl<Stock> implements StockDao{
	
	private static Logger logger = Logger.getLogger(StockDaoImpl.class);

	StockDaoImpl(){
		tableName = "fruit_stock";
		primaryKey = "stock_id";
	}

	@Override
	public Stock getGoodStock(String good_id) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from ").append(tableName).append(" where good_id=?");
		
		try {
			return jdbcTemplate.queryForObject(sql.toString(), new Object[]{good_id}, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}	}
}

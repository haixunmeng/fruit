package fruit.market.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fruit.market.dao.StockInDao;
import fruit.market.data.StockIn;
import fruit.market.exception.FruitException;

@Repository
public class StockInDaoImpl extends BaseDaoImpl<StockIn> implements StockInDao {
	
	private static Logger logger = Logger.getLogger(StockInDaoImpl.class);

	StockInDaoImpl() {
		tableName = "fruit_stock_in";
		primaryKey = "stock_batch_no";
	}
	
}

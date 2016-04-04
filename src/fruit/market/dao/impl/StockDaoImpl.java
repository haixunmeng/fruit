package fruit.market.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fruit.market.dao.StockDao;
import fruit.market.data.Stock;
import fruit.market.exception.FruitException;

@Repository
public class StockDaoImpl extends BaseDaoImpl<Stock> implements StockDao {
	
	private static Logger logger = Logger.getLogger(StockDaoImpl.class);

	StockDaoImpl() {
		tableName = "fruit_stock_in";
		primaryKey = "stock_batch_no";
	}
	
}

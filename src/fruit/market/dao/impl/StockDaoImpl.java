package fruit.market.dao.impl;

import org.springframework.stereotype.Repository;

import fruit.market.dao.StockDao;
import fruit.market.data.Stock;

@Repository
public class StockDaoImpl extends BaseDaoImpl<Stock> implements StockDao{

	StockDaoImpl(){
		tableName = "fruit_stock";
		primaryKey = "stock_id";
	}
}

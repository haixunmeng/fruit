package fruit.market.dao.impl;

import org.springframework.stereotype.Repository;

import fruit.market.dao.StockDetailDao;
import fruit.market.data.StockDetail;

@Repository
public class StockDetailDaoImpl extends BaseDaoImpl<StockDetail> implements StockDetailDao{

	StockDetailDaoImpl(){
		tableName = "fruit_stock_in_detail";
		primaryKey = "stock_detail_id";
	}
	
}

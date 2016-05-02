package fruit.market.dao.impl;

import org.springframework.stereotype.Repository;

import fruit.market.dao.StockInDetailDao;
import fruit.market.data.StockInDetail;

@Repository
public class StockInDetailDaoImpl extends BaseDaoImpl<StockInDetail> implements StockInDetailDao{

	StockInDetailDaoImpl(){
		tableName = "fruit_stock_in_detail";
		primaryKey = "stock_in_detail_id";
	}
	
}

package fruit.market.dao.impl;

import org.springframework.stereotype.Repository;

import fruit.market.dao.SellingDao;
import fruit.market.data.Selling;

@Repository
public class SellingDaoImpl extends BaseDaoImpl<Selling> implements SellingDao{

	SellingDaoImpl(){
		tableName = "fruit_selling";
		primaryKey = "good_id";
	}
	
}

package fruit.market.dao.impl;

import org.springframework.stereotype.Repository;

import fruit.market.dao.GoodDao;
import fruit.market.data.Good;

@Repository
public class GoodDaoImpl extends BaseDaoImpl<Good> implements GoodDao{

	GoodDaoImpl(){
		tableName = "fruit_good";
		primaryKey = "good_id";
	}
}

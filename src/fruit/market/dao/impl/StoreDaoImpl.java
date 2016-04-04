package fruit.market.dao.impl;

import org.springframework.stereotype.Repository;

import fruit.market.dao.StoreDao;
import fruit.market.data.Store;

@Repository
public class StoreDaoImpl extends BaseDaoImpl<Store> implements StoreDao {

	public StoreDaoImpl(){
		tableName = "fruit_store";
		primaryKey = "store_id";
	}
		
}

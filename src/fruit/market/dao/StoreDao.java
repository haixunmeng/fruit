package fruit.market.dao;

import org.springframework.stereotype.Repository;

import fruit.market.data.Store;

@Repository
public interface StoreDao extends BaseDao<Store>{

	Store getUserStore(String user_id);
	
}

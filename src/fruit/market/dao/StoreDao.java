package fruit.market.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fruit.market.data.Store;

@Repository
public interface StoreDao extends BaseDao<Store>{

	Store getUserStore(String user_id);

	public List<Store> getAllStore();

	List<Store> loadStores(Integer pageNum, Integer pageCount);
	
}

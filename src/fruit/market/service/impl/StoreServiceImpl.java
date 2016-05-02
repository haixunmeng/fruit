package fruit.market.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.dao.StoreDao;
import fruit.market.data.Store;
import fruit.market.service.StoreService;
import fruit.market.utils.DateUtil;
import fruit.market.utils.Utils;

@Service
public class StoreServiceImpl implements StoreService{
	
	@Autowired
	private StoreDao storeDao;

	@Override
	public void addStore(Map<String, String> params) {
		
		Store store = new Store();
		
		store.setStore_id(Utils.get_uuid());
		store.setSeller_id(params.get("seller_id"));
		store.setStore_name(params.get("store_name"));
		store.setAddress(params.get("address"));
		store.setCreate_time(DateUtil.getDateString());
		
		storeDao.add(store);
	}

}

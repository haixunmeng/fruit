package fruit.market.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.cache.CacheManager;
import fruit.market.dao.GoodDao;
import fruit.market.dao.SellingDao;
import fruit.market.dao.StockDao;
import fruit.market.dao.StoreDao;
import fruit.market.data.Good;
import fruit.market.data.Selling;
import fruit.market.data.Store;
import fruit.market.data.User;
import fruit.market.service.StoreService;
import fruit.market.utils.DateUtil;
import fruit.market.utils.Utils;

@Service
public class StoreServiceImpl implements StoreService{
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private SellingDao sellingDao;

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

	@Override
	public List<Store> getAllStore() {
		
		List<Store> stores = storeDao.getAllStore();
		return stores;
	}

	@Override
	public List<Map<String, String>> getAllStoresGoods(Map<String, Object> params) {
		
		String store_id = (String)params.get("store_id");
		
		if(store_id == null){
			User user = CacheManager.get((String)params.get("token"), User.class);
			Store store = storeDao.getUserStore(user.getUser_id());
			store_id = store.getStore_id();
		}
		
		List<Selling> sellingGoods = sellingDao.getStoreSellingGoods(store_id);
		
		List<Map<String, String>> sellingGoodsMap = new ArrayList<Map<String, String>>();
		
		for(Selling selling : sellingGoods){
			Map<String, String> sellingGoodMap = new HashMap<String, String>();
			
			sellingGoodMap.put("good_id", selling.getGood_id());
			Good good = goodDao.getData(selling.getGood_id());
			sellingGoodMap.put("good_name", good.getGood_name());
			
			sellingGoodsMap.add(sellingGoodMap);
		}
		
		return sellingGoodsMap;
		
	}

}

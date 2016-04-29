package fruit.market.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.cache.CacheManager;
import fruit.market.dao.GoodDao;
import fruit.market.dao.StockDao;
import fruit.market.dao.StockInDao;
import fruit.market.dao.StockInDetailDao;
import fruit.market.dao.StoreDao;
import fruit.market.data.Store;
import fruit.market.data.User;
import fruit.market.service.StockService;
import fruit.market.utils.DateUtil;

@Service
public class StockServiceImpl implements StockService{
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private StockInDao stockInDao;
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private StockInDetailDao stockInDetailDao;

	@Override
	public List<Map<String, Object>> getStock(Map<String, String> params){
		
		int pageNum = Integer.valueOf(params.get("pageNum"));
		int pageCount = Integer.valueOf(params.get("pageCount"));
		User user = CacheManager.get(params.get("token"), User.class);
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("user_id", user.getUser_id());
		List<Store> stores = storeDao.getByCondition(conditions);
		if(stores.size() == 0){
			
		}
		Store store = stores.get(0);
		
		conditions.clear();
		conditions.put("store_id", store.getStore_id());
		
		List<Map<String, Object>> stock = stockDao.getPageData(conditions, pageNum, pageCount);
		
		for(int i=0;i<stock.size();i++){
			Map<String, Object> stockDetail = stock.get(i);
			stockDetail.put("good_name", goodDao.getData(String.valueOf(stockDetail.get("good_id"))).getGood_name());
			conditions.clear();
			conditions.put("good_id", String.valueOf(stockDetail.get("good_id")));
			stockDetail.put("in_price", stockInDetailDao.getSingleByCondition(conditions).getIn_price());
			stockDetail.put("stock_in_time", DateUtil.formatDate(stockInDao.getData(String.valueOf(stockDetail.get("stock_batch_no"))).getCreate_time()));
		}
		
		return stock;
	}
}

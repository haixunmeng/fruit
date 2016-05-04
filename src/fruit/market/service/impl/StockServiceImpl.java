package fruit.market.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.cache.CacheManager;
import fruit.market.dao.GoodDao;
import fruit.market.dao.SellingDao;
import fruit.market.dao.StockDao;
import fruit.market.dao.StockInDao;
import fruit.market.dao.StockInDetailDao;
import fruit.market.dao.StoreDao;
import fruit.market.data.Selling;
import fruit.market.data.StockIn;
import fruit.market.data.StockInDetail;
import fruit.market.data.Store;
import fruit.market.data.User;
import fruit.market.exception.FruitException;
import fruit.market.service.StockService;
import fruit.market.utils.DateUtil;

@Service
public class StockServiceImpl implements StockService{
	
	private static Logger logger = Logger.getLogger(StockServiceImpl.class);
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private StockInDao stockInDao;
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private SellingDao sellingDao;
	
	@Autowired
	private StockInDetailDao stockInDetailDao;

	@Override
	public List<Map<String, Object>> getStock(Map<String, String> params){
		
		int pageNum = Integer.valueOf(params.get("pageNum"));
		int pageCount = Integer.valueOf(params.get("pageCount"));
		User user = CacheManager.get(params.get("token"), User.class);
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("seller_id", user.getUser_id());
		List<Store> stores = storeDao.getByCondition(conditions);
		if(stores.size() == 0){
			
		}
		Store store = stores.get(0);
		
		conditions.clear();
		conditions.put("store_id", store.getStore_id());
		
		List<Map<String, Object>> stock = stockDao.getPageMapData(conditions, pageNum, pageCount);
		
		for(int i=0;i<stock.size();i++){
			Map<String, Object> stockDetail = stock.get(i);
			stockDetail.put("good_name", goodDao.getData(String.valueOf(stockDetail.get("good_id"))).getGood_name());
			conditions.clear();
			conditions.put("good_id", String.valueOf(stockDetail.get("good_id")));
			stockDetail.put("in_price", stockInDetailDao.getSingleByCondition(conditions).getIn_price());
			StockInDetail stockInDetail = stockInDetailDao.getSingleByCondition(conditions);
			if(stockInDetail == null){
				logger.info(FruitException.NO_STOCK_IN_DETAIL_EXCEPTION);
				throw FruitException.NO_STOCK_IN_DETAIL_EXCEPTION;
			}
			
			conditions.clear();
			conditions.put("stock_in_batch_no", stockInDetail.getStock_in_batch_no());
			StockIn stockIn = stockInDao.getSingleByCondition(conditions);
			if(stockIn == null){
				logger.info(FruitException.NO_STOCK_IN_EXCEPTION);
				throw FruitException.NO_STOCK_IN_EXCEPTION;
			}
			stockDetail.put("stock_in_time", DateUtil.formatDate(stockIn.getCreate_time()));
			
			Selling selling = sellingDao.getData(String.valueOf(stockDetail.get("good_id")));
			if(selling == null){
				stockDetail.put("is_on_shelf", "未上架");
			}else{
				stockDetail.put("is_on_shelf", "已上架");
			}
		}
		
		return stock;
	}
}

package fruit.market.service.impl;

import java.math.BigDecimal;
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
import fruit.market.dao.StockInDao;
import fruit.market.dao.StockInDetailDao;
import fruit.market.dao.StoreDao;
import fruit.market.data.Good;
import fruit.market.data.GoodStatus;
import fruit.market.data.Selling;
import fruit.market.data.Stock;
import fruit.market.data.StockIn;
import fruit.market.data.StockInDetail;
import fruit.market.data.Store;
import fruit.market.data.User;
import fruit.market.service.SellingService;
import fruit.market.utils.DateUtil;

@Service
public class SellingServiceImpl implements SellingService{
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private SellingDao sellingDao;
	
	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private StockInDetailDao stockInDetailDao;
	
	@Autowired
	private StockInDao stockInDao;
	
	@Autowired
	private StockDao stockDao;

	@Override
	public List<Map<String, Object>> getAllStoreSellingGoods(Map<String, Object> params) {

		User user = CacheManager.get((String)params.get("token"), User.class);
		
		Store store = storeDao.getUserStore(user.getUser_id());
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("store_id", store.getStore_id());
		List<Selling> sellingGoods = sellingDao.getPageData(conditions, Integer.valueOf(String.valueOf(params.get("pageNum"))), Integer.valueOf(String.valueOf(params.get("pageCount"))));
		
		List<Map<String, Object>> sellingGoodsList = new ArrayList<Map<String, Object>>();
		
		for(Selling selling : sellingGoods){
			Map<String, Object> sellingMap = new HashMap<String, Object>();
			
			sellingMap.put("good_id", selling.getGood_id());
			sellingMap.put("sale_price", selling.getSale_price());
			sellingMap.put("value_unit", selling.getValue_unit());
			sellingMap.put("shelf_time", DateUtil.formatDate(selling.getCreate_time()));
			sellingMap.put("good_status", selling.getGood_status());
			
			Good good = goodDao.getData(selling.getGood_id());
			sellingMap.put("good_name", good.getGood_name());
			
			conditions.clear();
			conditions.put("good_id", selling.getGood_id());
			StockInDetail stockInDetail = stockInDetailDao.getSingleByCondition(conditions);
			sellingMap.put("in_price", stockInDetail.getIn_price());
			
			StockIn stockIn = stockInDao.getData(stockInDetail.getStock_in_batch_no());
			sellingMap.put("in_time", DateUtil.formatDate(stockIn.getCreate_time()));
			
			Stock stock = stockDao.getGoodStock(selling.getGood_id());
			sellingMap.put("left_num", stock.getLeft_num());
			
			sellingGoodsList.add(sellingMap);
		}
		
		return sellingGoodsList;
	}

	@Override
	public void unshelfGood(Map<String, String> params) {
		Selling selling = sellingDao.getData(params.get("good_id"));
		
		selling.setGood_status(GoodStatus.UNSELLING);
		
		sellingDao.update(selling);
	}

	@Override
	public void updateSalePrice(Map<String, String> params) {
		
		Selling selling = sellingDao.getData(params.get("good_id"));
		
		selling.setSale_price(new BigDecimal(String.valueOf(params.get("new_sale_price"))));
		
		sellingDao.update(selling);
		
	}

}

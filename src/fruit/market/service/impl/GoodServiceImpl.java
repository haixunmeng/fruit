package fruit.market.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.dao.GoodDao;
import fruit.market.dao.SellingDao;
import fruit.market.dao.StockDao;
import fruit.market.dao.StoreDao;
import fruit.market.dao.UserDao;
import fruit.market.data.Good;
import fruit.market.data.GoodStatus;
import fruit.market.data.Selling;
import fruit.market.data.Stock;
import fruit.market.data.Store;
import fruit.market.data.User;
import fruit.market.service.GoodService;

@Service
public class GoodServiceImpl implements GoodService{

	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private SellingDao sellingDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<Map<String, Object>> loadSellingGoods(Map<String, String> params){
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("good_status", GoodStatus.SELLING);
		List<Map<String, Object>> sellingGoods = sellingDao.getPageData(conditions, Integer.valueOf(params.get("pageNum")), Integer.valueOf(params.get("pageCount")));
		
		for(int i=0;i<sellingGoods.size();i++){
			Good good = goodDao.getData(String.valueOf(sellingGoods.get(i).get("good_id")));
			
			sellingGoods.get(i).put("good_name", good.getGood_name());
			sellingGoods.get(i).put("good_photo_url", good.getGood_photo_url());
			sellingGoods.get(i).put("good_name", good.getGood_name());
		}
		
		return sellingGoods;
	}

	@Override
	public Map<String, Object> loadGoodInfo(Map<String, String> params) {
		
		Map<String, Object> goodInfo = new HashMap<String, Object>();
		
		Good good = goodDao.getData(params.get("good_id"));
		
		if(good == null){
			
		}
		
		goodInfo.put("good_name", good.getGood_name());
		goodInfo.put("good_photo_url", good.getGood_photo_url());
		goodInfo.put("good_desc", good.getGood_desc());
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("good_id", params.get("good_id"));
		List<Selling> sellingGoods = sellingDao.getByCondition(conditions);
		
		Selling selling = sellingGoods.get(0);
		
		goodInfo.put("value_unit", selling.getValue_unit());
		goodInfo.put("sale_price", selling.getSale_price());
		goodInfo.put("store", selling.getStore_id());
		Store store = storeDao.getData(selling.getStore_id());
		goodInfo.put("store_name", store.getStore_name());
		User user = userDao.getData(store.getSeller_id());
		goodInfo.put("seller_name", user.getUser_name());
		
		Stock stock = stockDao.getSingleByCondition(conditions);
		goodInfo.put("left_num", stock.getLeft_num());
		
		return goodInfo;
	}
	
}

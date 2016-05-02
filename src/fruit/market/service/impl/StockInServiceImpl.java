package fruit.market.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.cache.CacheManager;
import fruit.market.dao.GoodDao;
import fruit.market.dao.StockDao;
import fruit.market.dao.StockInDao;
import fruit.market.dao.StockInDetailDao;
import fruit.market.dao.StoreDao;
import fruit.market.data.Good;
import fruit.market.data.Stock;
import fruit.market.data.StockIn;
import fruit.market.data.StockInDetail;
import fruit.market.data.Store;
import fruit.market.data.User;
import fruit.market.data.ValueUnit;
import fruit.market.service.StockInService;
import fruit.market.utils.DateUtil;
import fruit.market.utils.Utils;

@Service
public class StockInServiceImpl implements StockInService {
	
	private static Logger log = Logger.getLogger(StockInServiceImpl.class);
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private StockInDao stockInDao;
	
	@Autowired
	private StockInDetailDao stockInDetailDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private GoodDao goodDao;

	@Override
	public void StockIn(Map<String, Object> params) {

		User user = CacheManager.get((String)params.get("token"), User.class);
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("seller_id", user.getUser_id());
		Store store = storeDao.getSingleByCondition(conditions);
		
		StockIn stockIn = new StockIn();
		
		stockIn.setStore_id(store.getStore_id());
		stockIn.setStock_in_batch_no(Utils.get_uuid());
		stockIn.setTotal_price(new BigDecimal(String.valueOf(params.get("total_price"))));
		stockIn.setRemark(String.valueOf(params.get("remark")));
		stockIn.setCreate_time(DateUtil.getTimestamp());
		
		stockInDao.add(stockIn);
		
		List<StockInDetail> details = new ArrayList<StockInDetail>();
		
		List<Map<String, String>> stockDetails = (List<Map<String, String>>) params.get("stock_details");
		List<Stock> stock = new ArrayList<Stock>();
		List<Good> goods = new ArrayList<Good>();
		
		for(int i=0;i<stockDetails.size();i++){
			Map<String, String> detail = stockDetails.get(i);
			Stock stockDetail = new Stock();
			StockInDetail stockInDetail = new StockInDetail();
			
			stockInDetail.setStock_in_detail_id(Utils.get_uuid());
			stockInDetail.setStock_in_batch_no(stockIn.getStock_in_batch_no());
			stockInDetail.setGood_id(Utils.get_uuid());
			
			Good good = new Good();
			good.setGood_id(stockInDetail.getGood_id());
			good.setGood_name(detail.get("good_name"));
			goods.add(good);
			
			stockInDetail.setValue_unit(detail.get("value_unit"));
			stockInDetail.setIn_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("in_price")))));
			stockInDetail.setIn_num(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("in_num")))));
			stockInDetail.setTotal_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("total_price")))));
			stockInDetail.setRemark(detail.get("remark"));
			
			details.add(stockInDetail);
			
			stockDetail.setStock_id(Utils.get_uuid());
			stockDetail.setStore_id(store.getStore_id());
			stockDetail.setGood_id(stockInDetail.getGood_id());
			stockDetail.setValue_unit(detail.get("value_unit"));
			stockDetail.setLeft_num(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("in_num")))));
			stockDetail.setCreate_time(DateUtil.getDateString());
			
			stock.add(stockDetail);
		}
		
		stockInDetailDao.batchAdd(details);
		stockDao.batchAdd(stock);
		goodDao.batchAdd(goods);
	}

}

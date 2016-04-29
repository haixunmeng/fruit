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
		conditions.put("user_id", user.getUser_id());
		Store store = storeDao.getSingleByCondition(conditions);
		
		StockIn stockIn = new StockIn();
		
		stockIn.setStore_id(store.getStore_id());
		stockIn.setStock_batch_no(Utils.get_uuid());
		stockIn.setDeposit(BigDecimal.valueOf(Double.valueOf(String.valueOf(params.get("deposit")))));
		stockIn.setGood_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(params.get("good_price")))));
		stockIn.setTotal_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(params.get("total_price")))));
		stockIn.setCreate_time(DateUtil.getDateString());
		
		stockInDao.add(stockIn);
		
		List<StockInDetail> details = new ArrayList<StockInDetail>();
		
		List<Map<String, String>> stockDetails = (List<Map<String, String>>) params.get("stock_details");
		//Map<String, Object>[] stock = new Map[stockDetails.size()];
		List<Stock> stock = new ArrayList<Stock>();
		List<Good> goods = new ArrayList<Good>();
		
		for(int i=0;i<stockDetails.size();i++){
			Map<String, String> detail = stockDetails.get(i);
			//Map<String, Object> stockDetail = new HashMap<String, Object>();
			Stock stockDetail = new Stock();
			StockInDetail stockInDetail = new StockInDetail();
			
			stockInDetail.setStock_detail_id(Utils.get_uuid());
			stockInDetail.setStock_batch_no(stockIn.getStock_batch_no());
			stockInDetail.setGood_id(Utils.get_uuid());
			stockInDetail.setGood_name(detail.get("good_name"));
			
			Good good = new Good();
			good.setGood_id(stockInDetail.getGood_id());
			good.setGood_name(detail.get("good_name"));
			goods.add(good);
			
			stockInDetail.setValue_unit(detail.get("value_unit"));
			stockInDetail.setIn_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("in_price")))));
			stockInDetail.setPackage_type(detail.get("package_type"));
			stockInDetail.setPackage_weight(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("package_weight")))));
			stockInDetail.setPackage_deposit(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("package_deposit")))));
			stockInDetail.setPackage_num(Integer.valueOf(String.valueOf(detail.get("package_num"))));
			stockInDetail.setGross_weight(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("gross_weight")))));
			stockInDetail.setNet_weight(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("net_weight")))));
			stockInDetail.setTotal_deposit(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("total_deposit")))));
			stockInDetail.setTotal_goods_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("total_goods_price")))));
			stockInDetail.setTotal_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("total_price")))));
			stockInDetail.setRemark(detail.get("remark"));
			
			details.add(stockInDetail);
			
//			stockDetail.put("stock_id", Utils.get_uuid());
//			stockDetail.put("store_id", store.getStore_id());
//			stockDetail.put("good_id", stockInDetail.getGood_id());
//			stockDetail.put("stock_batch_no", stockIn.getStock_batch_no());
//			stockDetail.put("package_type", detail.get("package_type"));
//			stockDetail.put("value_unit", detail.get("value_unit"));
//			stockDetail.put("left_num", detail.get("value_unit").equals(ValueUnit.KILOGRAM)?detail.get("net_weight"):detail.get("package_num"));
//			stockDetail.put("create_time", DateUtil.getDateString());
			
			stockDetail.setStock_id(Utils.get_uuid());
			stockDetail.setStore_id(store.getStore_id());
			stockDetail.setGood_id(stockInDetail.getGood_id());
			stockDetail.setStock_batch_no(stockIn.getStock_batch_no());
			stockDetail.setPackage_type(detail.get("package_type"));
			stockDetail.setPackage_num(Integer.valueOf(String.valueOf(detail.get("package_num"))));
			stockDetail.setValue_unit(detail.get("value_unit"));
			stockDetail.setLeft_num(BigDecimal.valueOf(Double.valueOf(detail.get("value_unit").equals(ValueUnit.KILOGRAM)?String.valueOf(detail.get("net_weight")):String.valueOf(detail.get("package_num")))));
			stockDetail.setCreate_time(DateUtil.getDateString());
			
			stock.add(stockDetail);
		}
		
		stockInDetailDao.batchAdd(details);
		stockDao.batchAdd(stock);
		goodDao.batchAdd(goods);
	}

}

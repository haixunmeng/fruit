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

import fruit.market.dao.StockDao;
import fruit.market.dao.StockDetailDao;
import fruit.market.dao.StoreDao;
import fruit.market.data.Stock;
import fruit.market.data.StockDetail;
import fruit.market.data.Store;
import fruit.market.data.User;
import fruit.market.service.StockService;
import fruit.market.session.SessionManager;
import fruit.market.utils.DateUtil;
import fruit.market.utils.Utils;

@Service
public class StockServiceImpl implements StockService {
	
	private static Logger log = Logger.getLogger(StockServiceImpl.class);
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private StockDetailDao stockDetailDao;

	@Override
	public void StockIn(Map<String, Object> params) {

		User user = SessionManager.get((String)params.get("token"), User.class);
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("seller_id", user.getUser_id());
		Store store = storeDao.getSingleByCondition(conditions);
		
		Stock stock = new Stock();
		
		stock.setStore_id(store.getStore_id());
		stock.setStock_batch_no(Utils.get_uuid());
		stock.setDeposit(BigDecimal.valueOf(Double.valueOf(String.valueOf(params.get("deposit")))));
		stock.setGood_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(params.get("good_price")))));
		stock.setTotal_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(params.get("total_price")))));
		stock.setCreate_time(DateUtil.getDateString());
		
		stockDao.add(stock);
		
		List<StockDetail> details = new ArrayList<StockDetail>();
		
		List<Map<String, String>> stockDetails = (List<Map<String, String>>) params.get("stock_details");
		
		for(Map<String, String> detail : stockDetails){
			StockDetail stockDetail = new StockDetail();
			
			stockDetail.setStock_detail_id(Utils.get_uuid());
			stockDetail.setStock_batch_no(stock.getStock_batch_no());
			stockDetail.setGood_id(Utils.get_uuid());
			stockDetail.setGood_name(detail.get("good_name"));
			stockDetail.setValue_unit((String)params.get("value_unit"));
			stockDetail.setIn_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("in_price")))));
			stockDetail.setPackage_type(detail.get("package_type"));
			stockDetail.setPackage_weight(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("package_weight")))));
			stockDetail.setPackage_deposit(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("package_deposit")))));
			stockDetail.setPackage_num(Integer.valueOf(String.valueOf(detail.get("package_num"))));
			stockDetail.setGross_weight(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("gross_weight")))));
			stockDetail.setNet_weight(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("net_weight")))));
			stockDetail.setTotal_deposit(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("total_deposit")))));
			stockDetail.setTotal_goods_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("total_goods_price")))));
			stockDetail.setTotal_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(detail.get("total_price")))));
			stockDetail.setRemark(detail.get("remark"));
			
			details.add(stockDetail);
		}
		
		stockDetailDao.batchAdd(details);
	}

}

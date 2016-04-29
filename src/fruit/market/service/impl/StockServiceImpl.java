package fruit.market.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.dao.GoodDao;
import fruit.market.dao.StockDao;
import fruit.market.dao.StockInDao;
import fruit.market.dao.StockInDetailDao;
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
	private StockInDetailDao stockInDetailDao;

	@Override
	public List<Map<String, Object>> getStock(Map<String, String> params){
		
		int pageNum = Integer.valueOf(params.get("pageNum"));
		int pageCount = Integer.valueOf(params.get("pageCount"));
		
		List<Map<String, Object>> stock = stockDao.getPageData(pageNum, pageCount);
		
		for(int i=0;i<stock.size();i++){
			Map<String, Object> stockDetail = stock.get(i);
			stockDetail.put("good_name", goodDao.getData(String.valueOf(stockDetail.get("good_id"))).getGood_name());
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("good_id", String.valueOf(stockDetail.get("good_id")));
			stockDetail.put("in_price", stockInDetailDao.getSingleByCondition(conditions).getIn_price());
			System.out.println(stockInDao.getData(String.valueOf(stockDetail.get("stock_batch_no"))).getCreate_time());
			//System.out.println(DateUtil.formatDate(stockInDao.getData(String.valueOf(stockDetail.get("stock_batch_no"))).getCreate_time()));
			stockDetail.put("stock_in_time", stockInDao.getData(String.valueOf(stockDetail.get("stock_batch_no"))).getCreate_time());
		}
		
		return stock;
	}
}

package fruit.market.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.dao.GoodDao;
import fruit.market.dao.SellingDao;
import fruit.market.data.Good;
import fruit.market.service.GoodService;

@Service
public class GoodServiceImpl implements GoodService{

	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private SellingDao sellingDao;
	
	@Override
	public List<Map<String, Object>> loadSellingGoods(Map<String, String> params){
		
		List<Map<String, Object>> sellingGoods = sellingDao.getPageData(Integer.valueOf(params.get("pageNum")), Integer.valueOf(params.get("pageCount")));
		
		for(int i=0;i<sellingGoods.size();i++){
			Good good = goodDao.getData(String.valueOf(sellingGoods.get(i).get("good_id")));
			
			sellingGoods.get(i).put("good_name", good.getGood_name());
			sellingGoods.get(i).put("good_photo_url", good.getGood_photo_url());
			sellingGoods.get(i).put("good_name", good.getGood_name());
		}
		
		return sellingGoods;
	}
	
}

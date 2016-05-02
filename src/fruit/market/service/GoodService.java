package fruit.market.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import fruit.market.data.Good;

@Service
public interface GoodService {

	public List<Map<String, Object>> loadSellingGoods(Map<String, String> params);
	
	public Map<String, Object> loadGoodInfo(Map<String, String> params);
	
}

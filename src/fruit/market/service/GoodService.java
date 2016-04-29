package fruit.market.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface GoodService {

	public List<Map<String, Object>> loadSellingGoods(Map<String, String> params);
	
}

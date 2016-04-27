package fruit.market.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface StockService {

	public List<Map<String, Object>> getStock(Map<String, String> params);
	
}

package fruit.market.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface StockService {

	public void StockIn(Map<String, Object> params);
	
}

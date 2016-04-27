package fruit.market.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface StockInService {

	public void StockIn(Map<String, Object> params);
	
}

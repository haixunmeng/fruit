package fruit.market.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface SellingService {

	public List<Map<String, Object>> getAllStoreSellingGoods(Map<String, Object> params);

	public void unshelfGood(Map<String, String> params);

	public void updateSalePrice(Map<String, String> params);

}

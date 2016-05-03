package fruit.market.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import fruit.market.data.Store;

@Service
public interface StoreService {

	void addStore(Map<String, String> params);

	public List<Store> getAllStore();

	public List<Map<String, String>> getAllStoresGoods(Map<String, Object> params);

}

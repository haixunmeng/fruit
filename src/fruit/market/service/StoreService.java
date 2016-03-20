package fruit.market.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface StoreService {

	void addStore(Map<String, String> params);

}

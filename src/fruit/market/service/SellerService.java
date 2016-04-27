package fruit.market.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public interface SellerService {

	public void shelfGood(HttpServletRequest request);
	
}

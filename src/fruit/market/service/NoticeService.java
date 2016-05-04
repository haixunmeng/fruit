package fruit.market.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface NoticeService {
	
	public void addNotice(Map<String, String> params);

	public String getLatestNotice();
}

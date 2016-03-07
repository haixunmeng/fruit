package fruit.market.service;


import java.util.Map;

import org.springframework.stereotype.Service;

import fruit.market.model.User;


@Service
public interface UserService {
	
	public void register(Map<String, Object> parameters);

	public void login(Map<String, Object> params);
}

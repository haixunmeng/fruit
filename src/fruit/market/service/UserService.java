package fruit.market.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import fruit.market.data.User;


@Service
public interface UserService {
	
	public void register(Map<String, String> parameters);

	public void login(Map<String, String> params);
	
	public void logout(Map<String, String> params);

	public void addSeller(Map<String, String> params);

	public List<Map<String, String>> getAllSeller(Map<String, String> params);
	
	public List<Map<String, Object>> getUsers(Map<String, String> params);

	public List<Map<String, String>> getAllBuyer();

	public void updatePwd(Map<String, String> params);

	public void lockUser(Map<String, String> params);

	public void unlockUser(Map<String, String> params);
}

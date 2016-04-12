package fruit.market.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.dao.AuthDao;
import fruit.market.data.Resource;
import fruit.market.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private AuthDao authDao;
	
	public List<Resource> getUserMenu(String sup_resource_id, String role){
	
		Map<String, Object> cons = new HashMap<String, Object>();
		cons.put("sup_resource_id", sup_resource_id);
		cons.put("role", role);
		List<Resource> resources = authDao.getByCondition(cons);
		
		return resources;
	}
	
}

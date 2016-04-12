package fruit.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fruit.market.data.Resource;

@Service
public interface AuthService {

	public List<Resource> getUserMenu(String sup_resource_id, String role);
	
}

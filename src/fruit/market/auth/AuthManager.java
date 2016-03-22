package fruit.market.auth;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.dao.AuthDao;
import fruit.market.data.Resource;
import fruit.market.data.Role;
import fruit.market.exception.FruitException;
import fruit.market.session.SessionManager;

@Service
public class AuthManager {
	
	private static Logger logger = Logger.getLogger(AuthManager.class);

	@Autowired
	private AuthDao authDao;
	
	private static AuthManager authManager;
	
	private AuthManager(){
		
	}
	
	public static AuthManager getInstance(){
		if(null == authManager){
			authManager = new AuthManager();
		}
		
		return authManager;
	}
	
	public void validateAuth(String action, String token){

		Resource resource = authDao.getData(action);
		
		String actionRole = resource.getRole();
		
		if(Role.COMMON.equals(actionRole)){
			return;
		}else if(null == token || "" == token){
			logger.info(FruitException.NO_AUTH_EXCEPTION);
			throw FruitException.NO_AUTH_EXCEPTION;
		}
		
		String tokenRole = SessionManager.get4session(token, "type");
		
		if(!actionRole.equals(tokenRole)){
			logger.info(FruitException.NO_AUTH_EXCEPTION);
			throw FruitException.NO_AUTH_EXCEPTION;
		}
	}
	
}

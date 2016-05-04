package fruit.market.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import fruit.market.cache.CacheManager;
import fruit.market.dao.AuthDao;
import fruit.market.data.Role;
import fruit.market.data.User;
import fruit.market.data.UserStatus;
import fruit.market.exception.FruitException;

@Aspect
@Service
public class AuthManager {

	private static Logger logger = Logger.getLogger(AuthManager.class);

	@Autowired
	private AuthDao authDao;

	@Around("execution(* fruit.market.controller.*.*(..))")
	public Object authFilter(ProceedingJoinPoint pjp) throws Throwable {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		String action = request.getRequestURI();

		Cookie[] cookies = request.getCookies();

		String token = "";
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					token = cookie.getValue();
				}
			}
		}

		try {
			validateAuth(action, token);
		} catch (FruitException e) {
			Map<String, Object> resMeg = new HashMap<String, Object>();
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
			return resMeg;
		}

		return pjp.proceed();
	}

	public void validateAuth(String action, String token){

		List<String> authRoles = authDao.getAuthRole(action);
		User user = CacheManager.get(token, User.class);
		
		if(null == authRoles || authRoles.size() == 0){
			logger.info(FruitException.NO_AUTH_EXCEPTION);
			throw FruitException.NO_AUTH_EXCEPTION;
		}else if(authRoles.size() == 1 && Role.COMMON.equals(authRoles.get(0))){
			return;
		}
		
		if(null == user){
			logger.info(FruitException.NO_AUTH_EXCEPTION);
			throw FruitException.NO_AUTH_EXCEPTION;
		}
		
		if(UserStatus.LOCKED.equals(user.getUser_status())){
			logger.info(FruitException.USER_IS_LOCKED_EXCEPTION);
			throw FruitException.USER_IS_LOCKED_EXCEPTION;
		}
		
		String tokenRole = user.getUser_type();
		
		if(authRoles.contains(tokenRole)){
			return;
		}else{
			logger.info(FruitException.NO_AUTH_EXCEPTION);
			throw FruitException.NO_AUTH_EXCEPTION;
		}
	}
}

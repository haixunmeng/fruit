package fruit.market.auth;

import java.util.HashMap;
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
import fruit.market.data.Resource;
import fruit.market.data.Role;
import fruit.market.data.User;
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

		Resource resource = authDao.getData(action);
		
		if(null == resource){
			logger.info(FruitException.NO_AUTH_EXCEPTION);
			throw FruitException.NO_AUTH_EXCEPTION;
		}
		
		String actionRole = resource.getRole();
		
		if(Role.COMMON.equals(actionRole)){
			return;
		}
		if(Role.PROTECTED.equals(actionRole)){
			if(null != token && "" != token){
				return;
			}
		}else if(null == token || "" == token){
			logger.info(FruitException.NO_AUTH_EXCEPTION);
			throw FruitException.NO_AUTH_EXCEPTION;
		}
		
		User user = CacheManager.get(token, User.class);
		
		String tokenRole = user.getUser_type();
		
		if(!actionRole.equals(tokenRole)){
			logger.info(FruitException.NO_AUTH_EXCEPTION);
			throw FruitException.NO_AUTH_EXCEPTION;
		}
	}
}

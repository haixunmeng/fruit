package fruit.market.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.auth.AuthManager;
import fruit.market.exception.FruitException;
import fruit.market.utils.Utils;

@Service
public class AuthFilter implements Filter {

	private static Logger logger = Logger.getLogger(AuthFilter.class);
	
	@Autowired
	private AuthManager authManager;

	public AuthFilter() {
		
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;

		String action = req.getRequestURI();
		logger.info("action : " + action);

		Cookie[] cookies = req.getCookies();

		String token = "";
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if("token".equals(cookie.getName())){
					token = cookie.getValue();
				}
			}
		}
		
		try{
			authManager.validateAuth(action, token);
		} catch (FruitException e){
			Map<String, Object> resMeg = new HashMap<String, Object>();
			resMeg.put("code", e.errorCode);
			resMeg.put("msg", e.errorMsg);
			Utils.writeReponse(rep, resMeg);
			return;
		}

		chain.doFilter(req, rep);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}

package fruit.market.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import fruit.market.utils.PrivilegeUtil;
import fruit.market.utils.Utils;

@WebFilter(filterName = "PrivilegeFilter", urlPatterns = "/*")
public class PrivilegeFilter implements Filter {

	private static Logger logger = Logger.getLogger(PrivilegeFilter.class);

	public PrivilegeFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		logger.info("...................... begin filter .............");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;

		String action = req.getRequestURI();
		
		String actionType = "";

		if (Utils.contains(PrivilegeUtil.commonActions, action)) {
			actionType = "C";
		} else if (Utils.contains(PrivilegeUtil.sellerActions, action)) {
			actionType = "S";
		} else if (Utils.contains(PrivilegeUtil.buyerActions, action)) {
			actionType = "B";
		} else if (Utils.contains(PrivilegeUtil.managerActions, action)) {
			actionType = "M";
		}

		Cookie[] cookies = req.getCookies();

		if (null != cookies) {
			for (Cookie cookie : cookies) {
				logger.info(cookie.getName() + "  " + cookie.getValue());
			}
		}

		chain.doFilter(req, rep);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}

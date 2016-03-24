package fruit.market.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebFilter(filterName = "delegatingFilterProxy", urlPatterns = "*.do", initParams = {
		@WebInitParam(name = "targetFilter", value = "authFilter") })
public class DelegatingFilterProxy implements Filter {
	
	private Filter targetFilter;

	public DelegatingFilterProxy() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		targetFilter.doFilter(request, response, chain);
	}

	public void init(FilterConfig fConfig) throws ServletException {

		WebApplicationContext wc = WebApplicationContextUtils.getRequiredWebApplicationContext(fConfig.getServletContext());
		
		this.targetFilter = (Filter) wc.getBean(fConfig.getInitParameter("targetFilter"));
		
		this.targetFilter.init(fConfig);
	}

}

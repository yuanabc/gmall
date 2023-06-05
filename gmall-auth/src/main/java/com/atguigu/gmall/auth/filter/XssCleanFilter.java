package com.atguigu.gmall.auth.filter;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description TODO
 * @Date 2020/8/28 22:31
 * @Created by yuant
 */
@WebFilter(filterName = "xssCleanFilter", urlPatterns = "/*", asyncSupported = true)
public class XssCleanFilter implements Filter {

	public XssCleanFilter() {
//		InputStream is = this.getClass().getResourceAsStream("/antisamy/antisamy-ebay.xml");
//		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("antisamy/antisamy-ebay.xml");
//		try {
//			Policy policy = Policy.getInstance(inputStream);
//			antiSamy = new AntiSamy(policy);
//		} catch (PolicyException e) {
//			throw new IllegalStateException(e.getMessage(), e);
//		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			CleanServletRequest cleanRequest = new CleanServletRequest((HttpServletRequest) request);
			chain.doFilter(cleanRequest, response);
		} else {
			chain.doFilter(request, response);
		}
	}


}

package com.atguigu.gmall.auth.filter;

import com.atguigu.gmall.auth.util.XssUtils;
import lombok.extern.slf4j.Slf4j;
import org.owasp.validator.html.AntiSamy;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description TODO
 * @Date 2020/8/28 22:44
 * @Created by yuant
 */
@Slf4j
public class CleanServletRequest extends HttpServletRequestWrapper{


	/**
	 * Constructs a request object wrapping the given request.
	 *
	 * @param request The request to wrap
	 * @throws IllegalArgumentException if the request is null
	 */
	public CleanServletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getQueryString() {
		return super.getQueryString();
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] originalValues = super.getParameterValues(name);
		if (originalValues == null) {
			return null;
		}
		List<String> newValues = new ArrayList<String>(originalValues.length);
		for (String value : originalValues) {
			newValues.add(XssUtils.clean(value));
		}
		return newValues.toArray(new String[newValues.size()]);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> originalMap = super.getParameterMap();
		Map<String, String[]> filteredMap = new ConcurrentHashMap<String, String[]>(originalMap.size());
		for (String name : originalMap.keySet()) {
			filteredMap.put(name, getParameterValues(name));
		}
		return Collections.unmodifiableMap(filteredMap);
	}

	@Override
	public String getParameter(String name) {
		String parameter = super.getParameter(name);
		return XssUtils.clean(parameter);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return super.getInputStream();
	}



}

package com.atguigu.gmall.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @Description TODO
 * @Date 2020/8/29 11:16
 * @Created by yuant
 */
@Slf4j
public class XssUtils {

	private static AntiSamy antiSamy;

	static {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("classpath:/antisamy/antisamy-ebay.xml");
		String path = null;
		try {
			path = resource.getURL().getPath();
			Policy policy = Policy.getInstance(path);
			antiSamy = new AntiSamy(policy);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PolicyException e) {
			e.printStackTrace();
		}
	}

	public static String clean(String value){
		return filterString(value);
	}

	public static String[] clean(String[] values){
		if(values == null || values.length == 0){
			return values;
		}
		int len = values.length;
		String[] _values = new String[len];
		for(int i = 0; i < len; i++){
			_values[i] = clean(values[i]);
		}
		return _values;
	}

	private static String filterString(String s) {
		if (s == null) {
			return null;
		}
		try {
			CleanResults cr = antiSamy.scan(s, AntiSamy.DOM);
			if (cr.getNumberOfErrors() > 0) {
				log.warn("antisamy encountered problem with input: " + cr.getErrorMessages());
			}
			String str = StringEscapeUtils.unescapeHtml(cr.getCleanHTML());
			str = str.replaceAll((antiSamy.scan("&nbsp;",AntiSamy.DOM)).getCleanHTML(),"");
			return str;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}

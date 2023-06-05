package com.atguigu.gmall.auth.security.jwt;

import com.atguigu.gmall.auth.entity.ValidateCode;
import com.atguigu.gmall.auth.exception.ValidateCodeException;
import com.atguigu.gmall.auth.security.constant.ValidateCodeTypeEnum;
import com.atguigu.gmall.auth.security.constant.BrowserSecurityProperties;
import com.atguigu.gmall.auth.service.ValidateCodeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	private Map<String, ValidateCodeTypeEnum> urlMap = new LinkedHashMap<>();
	
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	private ValidateCodeService validateCodeRepository;

	private BrowserSecurityProperties browserSecurityProperties;

	//bean初使化完成后 需要验证 验证码 地址 与验证码类型的匹配，同时给定提交验证码的参数
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		
		urlMap.put(BrowserSecurityProperties.DEFAULT_SIGNIN_PROCESS_URL_FORM, ValidateCodeTypeEnum.IMAGE);

		//将配置文件中需要图片验证码验证的地址进行配置
		addUrlToMap(browserSecurityProperties.getUrl(), ValidateCodeTypeEnum.IMAGE);
	}

	private void addUrlToMap(String validateCodeUrl, ValidateCodeTypeEnum validateCodeType) {
		String[] validateCodeUrls = StringUtils.splitByWholeSeparator(validateCodeUrl, ",");
		for (String url : validateCodeUrls) {
			urlMap.put(url, validateCodeType);
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		logger.info("ValidateCodeFilter: " + request.getRequestURL());
		
		ValidateCodeTypeEnum validateCodeType = null;
		for (Entry<String, ValidateCodeTypeEnum> entry : urlMap.entrySet()) {
			if (antPathMatcher.match(entry.getKey(), request.getRequestURI())) {
				validateCodeType = entry.getValue();
				break;
			}
		}
		
		if (validateCodeType != null) {
			try {
				validate(request, validateCodeType);
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		
		filterChain.doFilter(request, response);
	}

	private void validate(HttpServletRequest request, ValidateCodeTypeEnum needCodeType) throws ServletRequestBindingException {
		
		ValidateCode codeInRepository = validateCodeRepository.getCode(request, needCodeType);
		
		if (codeInRepository == null) {
			throw new ValidateCodeException(String.format("需要%s验证码", needCodeType));
		}
		
		String codeInRequest = getCodeInRequest(request, needCodeType);
		
		// 验证码不能为空
		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("请填写验证码");
		}
		
		// 验证码不存在
		if (codeInRequest == null) {
			throw new ValidateCodeException("请先获取验证码");
		}
		
		// 验证码已过期
		if (codeInRepository.isExpired()) {
			validateCodeRepository.remove(request, needCodeType);
			throw new ValidateCodeException("验证码已过期");
		}
		
		// 验证码不匹配
		if (!StringUtils.equalsIgnoreCase(codeInRepository.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		
		validateCodeRepository.remove(request, needCodeType);
	}
	
	private String getCodeInRequest(HttpServletRequest request, ValidateCodeTypeEnum validateCodeType) {
		//imageCode
		String codeInRequest = request.getParameter(validateCodeType.getParameterNameOnValidate());
		return codeInRequest;
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public ValidateCodeService getValidateCodeRepository() {
		return validateCodeRepository;
	}

	public void setValidateCodeRepository(ValidateCodeService validateCodeRepository) {
		this.validateCodeRepository = validateCodeRepository;
	}

	public BrowserSecurityProperties getBrowserSecurityProperties() {
		return browserSecurityProperties;
	}

	public void setBrowserSecurityProperties(BrowserSecurityProperties browserSecurityProperties) {
		this.browserSecurityProperties = browserSecurityProperties;
	}
}

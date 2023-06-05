package com.atguigu.gmall.auth.service.impl;

import com.atguigu.gmall.auth.entity.ValidateCode;
import com.atguigu.gmall.auth.exception.ValidateCodeException;
import com.atguigu.gmall.auth.security.constant.ValidateCodeTypeEnum;
import com.atguigu.gmall.auth.service.ValidateCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

	public final static String VALIDATECODE_SESSIONKEY_PREFIX = "SESSION_KEY_FOR_CODE_";

	private String getSessionKey(ValidateCodeTypeEnum codeTypeEnum) {
		StringBuilder buff = new StringBuilder(VALIDATECODE_SESSIONKEY_PREFIX);
		buff.append(codeTypeEnum);
		return buff.toString();
	}


	@Override
	public void saveImageCode(HttpServletRequest request, ValidateCode imageCode, ValidateCodeTypeEnum codeTypeEnum) {
		request.getSession().setAttribute(getSessionKey(codeTypeEnum), imageCode);
	}

	@Override
	public ValidateCode getCode(HttpServletRequest request, ValidateCodeTypeEnum codeTypeEnum) {
		Object objectInSession = request.getSession().getAttribute(getSessionKey(codeTypeEnum));
		if (objectInSession != null) {
			return (ValidateCode) objectInSession;
		} else {
			return null;
		}
	}

	@Override
	public void remove(HttpServletRequest request, ValidateCodeTypeEnum codeTypeEnum) {
		request.getSession().removeAttribute(getSessionKey(codeTypeEnum));
	}

	/**
	 * 验证验证码
	 */
	@Override
	public void validate(HttpServletRequest request) {
		ValidateCode codeInRepository = this.getCode(request, ValidateCodeTypeEnum.IMAGE);
		if (codeInRepository == null) {
			throw new ValidateCodeException(String.format("需要%s验证码", ""));
		}
		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request, "validCode");
		} catch (ServletRequestBindingException e) {
			throw new AuthenticationException("获取验证码的值失败"){};
		}
		if (codeInRequest == null || StringUtils.isBlank(codeInRequest)) {
			throw new AuthenticationException("请填写验证码"){};
		}

		if (codeInRepository.isExpired()) {
			throw new AuthenticationException("验证码不存在或已过期"){};
		}

		if (!StringUtils.equalsIgnoreCase(codeInRepository.getCode(), codeInRequest)) {
			throw new AuthenticationException("验证码不正确"){};
		}

		this.remove(request, ValidateCodeTypeEnum.IMAGE);
	}

	private String buildKey(String deviceId) {
		return "DEFAULT_CODE_KEY:" + deviceId;
	}

//	@Override
//	public void validate(String deviceId, String validCode) {
//		if (StringUtils.isBlank(deviceId)) {
//			throw new AuthenticationException("请在请求参数中携带deviceId参数"){};
//		}
//		String code = this.getCode(deviceId);
//
//		if (StringUtils.isBlank(validCode)) {
//			throw new AuthenticationException("请填写验证码"){};
//		}
//
//		if (code == null) {
//			throw new AuthenticationException("验证码不存在或已过期"){};
//		}
//
//		if (!StringUtils.equalsIgnoreCase(code, validCode)) {
//			throw new AuthenticationException("验证码不正确"){};
//		}
//
//		this.remove(deviceId);
//	}
}

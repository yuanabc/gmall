package com.atguigu.gmall.auth.security.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2020/8/28 0:03
 * @Created by yuant
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "security.browser")
public class BrowserSecurityProperties {

	private String url;

	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
	 */
	public static final String DEFAULT_REQUEST_PARAMETER_IMAGECODE = "imageCode";

	/**
	 * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
	 */
	public static final String DEFAULT_REQUEST_PARAMETER_SMSCODE= "smsCode";

	/**
	 * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
	 */
	public static final String DEFAULT_REQUEST_PARAMETER_MOBILE = "mobile";

	/**
	 * 默认的用户名密码登录请求处理url
	 */
	public static final String DEFAULT_SIGNIN_PROCESS_URL_FORM = "/login/userLogin";

}

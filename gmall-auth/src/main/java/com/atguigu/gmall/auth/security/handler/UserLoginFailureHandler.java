package com.atguigu.gmall.auth.security.handler;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.auth.exception.ValidateCodeException;
import com.atguigu.gmall.auth.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 登录失败处理类
 */
@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		// 这些对于操作的处理类可以根据不同异常进行不同处理
		if (exception instanceof UsernameNotFoundException) {
			log.info("【登录失败】" + exception.getMessage());
			ResultUtil.responseJson(response, Resp.fail( "用户名不存在"));
			return;
		}
		if (exception instanceof LockedException) {
			log.info("【登录失败】" + exception.getMessage());
			ResultUtil.responseJson(response, Resp.fail( "用户被冻结"));
			return;
		}
		if (exception instanceof BadCredentialsException) {
			log.info("【登录失败】" + exception.getMessage());
			ResultUtil.responseJson(response, Resp.fail( "用户名密码不正确"));
			return;
		}
		if (exception instanceof ValidateCodeException) {
			log.info("【登录失败】" + exception.getMessage());
			ResultUtil.responseJson(response, Resp.fail( "验证码错误"));
			return;
		}
		ResultUtil.responseJson(response, Resp.fail( "登录失败"));
	}
}
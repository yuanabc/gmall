package com.atguigu.gmall.auth.security.handler;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.auth.util.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户未登录处理类
 */
@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){
        //403
        ResultUtil.responseJson(response, Resp.fail(exception.getMessage()));
    }
}
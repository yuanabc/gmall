package com.atguigu.gmall.auth.config;

import com.atguigu.gmall.auth.security.UserAuthenticationProvider;
import com.atguigu.gmall.auth.security.UserPermissionEvaluator;
import com.atguigu.gmall.auth.security.constant.BrowserSecurityProperties;
import com.atguigu.gmall.auth.security.handler.*;
import com.atguigu.gmall.auth.security.jwt.JWTAuthenticationTokenFilter;
import com.atguigu.gmall.auth.security.jwt.ValidateCodeFilter;
import com.atguigu.gmall.auth.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;
    /**
     * 自定义登录失败处理器
     */
    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;
    /**
     * 自定义注销成功处理器
     */
    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;
    /**
     * 自定义暂无权限处理器
     */
    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    /**
     * 自定义未登录的处理器
     */
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
    /**
     * 自定义登录逻辑验证器
     */
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    /**
     * 验证码验证service
     */
    @Autowired
    private ValidateCodeService validateCodeRepository;

    @Autowired
    private BrowserSecurityProperties browserSecurityProperties;

    /**
     * 加密方式
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }

    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }
    /**
     * 配置security的控制逻辑
     * @Param  http 请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        //不把filter托管给spring，直接new,  避免filter执行两次问题
        validateCodeFilter.setValidateCodeRepository(validateCodeRepository);
        validateCodeFilter.setAuthenticationFailureHandler(userLoginFailureHandler);
        validateCodeFilter.setBrowserSecurityProperties(browserSecurityProperties);
        validateCodeFilter.afterPropertiesSet();

        http
                .authorizeRequests()
                // 不进行权限验证的请求或资源(从配置文件中读取)
               .antMatchers(JWTConfig.antMatchers.split(",")).permitAll()
                // 其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                // 配置未登录自定义处理类
                .httpBasic()/*.authenticationEntryPoint(userAuthenticationEntryPointHandler)*/
                .and()
                // 添加JWT过滤器
                .addFilter(new JWTAuthenticationTokenFilter(authenticationManager()))
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置登录地址
                .formLogin()
                .loginProcessingUrl(BrowserSecurityProperties.DEFAULT_SIGNIN_PROCESS_URL_FORM)
                // 配置登录成功自定义处理类
                .successHandler(userLoginSuccessHandler)
                // 配置登录失败自定义处理类
                .failureHandler(userLoginFailureHandler)
                .and()
                // 配置登出地址
                .logout()
                .logoutUrl("/login/userLogout")
                // 配置用户登出自定义处理类
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                // 配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();
        // 基于Token不需要session
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();

    }
}
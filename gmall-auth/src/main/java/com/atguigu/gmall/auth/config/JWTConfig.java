package com.atguigu.gmall.auth.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 */
@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {
    /**
     * 密钥KEY
     */
    public static String secret = "aaabbbcccdddeeefffggghhhiiissssssjjjkkklllmmmnnnooopppqqqrrrsssttt@#";
    /**
     * TokenKey
     */
    public static String tokenHeader = "Authorization";
    /**
     * Token前缀字符
     */
    public static String tokenPrefix="bearer ";
    /**
     * 过期时间 2天后
     */
    public static Integer expiration = 2*24*3600;
    /**
     * 不需要认证的接口
     */
    public static String antMatchers;


    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    public void setAntMatchers(String antMatchers) {
        this.antMatchers = antMatchers;
    }
}
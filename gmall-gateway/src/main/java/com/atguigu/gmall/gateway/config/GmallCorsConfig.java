package com.atguigu.gmall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @Description TODO
 * Spring已经帮我们写好了CORS的跨域过滤器，内部已经实现了刚才所讲的判定逻辑
 * spring-webmvc：CorsFilter
 * spring-webflux：CorsWebFilter
 *
 * @Date 2020/8/23 17:24
 * @Created by yuant
 */
@Configuration
public class GmallCorsConfig {

	@Bean
	public CorsWebFilter corsWebFilter(){
		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setMaxAge(18000L);
		configSource.registerCorsConfiguration("/**", config);
		return new CorsWebFilter(configSource);
	}

}

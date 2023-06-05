package com.atguigu.gmall.auth.config;

import com.atguigu.gmall.auth.util.SpringContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description TODO
 * @Date 2020/8/29 11:33
 * @Created by yuant
 */
@Configuration
public class WebMVCConfig extends WebMvcConfigurationSupport {

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
		while (iterator.hasNext()){
			HttpMessageConverter<?> next = iterator.next();
			if(next instanceof MappingJackson2HttpMessageConverter){
				iterator.remove();
				break;
			}
		}
		converters.add(getMappingJackson2HttpMessageConverter());
	}

	private HttpMessageConverter<?> getMappingJackson2HttpMessageConverter() {
		// 创建自定义ObjectMapper
		SimpleModule simpleModule = new SimpleModule();
		// XSS序列化
		simpleModule.addSerializer(String.class, new XssJacksonSerializer());
		simpleModule.addDeserializer(String.class, new XssJacksonDeserializer());
//		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(this.getApplicationContext()).build();
		ObjectMapper objectMapper = SpringContextHolder.getBean(ObjectMapper.class);
		objectMapper.registerModule(simpleModule);
		// 创建自定义消息转换器
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
		//设置中文编码格式
		List<MediaType> list = new ArrayList<>();
		list.add(MediaType.APPLICATION_JSON_UTF8);
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
		return mappingJackson2HttpMessageConverter;
	}
}

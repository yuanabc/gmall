package com.atguigu.gmall.auth.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2020/9/6 23:10
 * @Created by yuant
 */
@Configuration
public class JacksonConfiguration {

	@Bean
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder){
		ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
		//map里的null值序列化
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);

		//空值不序列化
		//objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		//将所有为null的处理成"" 包含Collection类型
//		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){
//			@Override
//			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
//				gen.writeString("");
//			}
//		} );
		//单引号处理
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		//反序列化时，属性不存在的兼容处理
		objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		//序列化 反序列化时对未知字段忽略
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//序列化时，日期的统一格式
		// 重新设置日期序列化格式
		com.fasterxml.jackson.databind.module.SimpleModule simpleModule = new com.fasterxml.jackson.databind.module.SimpleModule();
		simpleModule.addDeserializer(Date.class, new DateDeserializer());
		simpleModule.addSerializer(new DateSerializer2(Date.class));
		objectMapper.registerModule(simpleModule);
		objectMapper.findAndRegisterModules();
		return objectMapper;
	}

}

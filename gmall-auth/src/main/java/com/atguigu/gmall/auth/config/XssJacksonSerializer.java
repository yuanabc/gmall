package com.atguigu.gmall.auth.config;

import com.atguigu.gmall.auth.util.XssUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Description TODO
 * @Date 2020/8/29 11:12
 * @Created by yuant
 */
public class XssJacksonSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeString(XssUtils.clean(s));
	}
}

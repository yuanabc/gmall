package com.atguigu.gmall.auth.config;

import com.atguigu.gmall.auth.util.XssUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @Description TODO
 * @Date 2020/8/29 11:11
 * @Created by yuant
 */
public class XssJacksonDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		return XssUtils.clean(jsonParser.getText());
	}
}

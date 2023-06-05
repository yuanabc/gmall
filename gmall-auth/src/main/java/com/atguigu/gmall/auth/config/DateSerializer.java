package com.atguigu.gmall.auth.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2020/9/6 20:39
 * @Created by yuant
 */
public class DateSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			gen.writeString(sdf.format(value));
		}
	}

	@Override
	public Class<Date> handledType() {
		return Date.class;
	}

}

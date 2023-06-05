package com.atguigu.gmall.auth.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @Description TODO 自定义Jackson反序列化日期类型时应用的类型转换器,一般用于@RequestBody接受参数时使用
 * @Date 2020/9/6 20:59
 * @Created by yuant
 */
public class DateDeserializer extends JsonDeserializer<Date> {

	private static String[] pattern =
			new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S",
					"yyyy.MM.dd", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss.S",
					"yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.S"};

	@Override
	public Date deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		Date targetDate = null;
		String originDate = p.getText();
		if (StringUtils.isNotEmpty(originDate)) {
			try {
				long longDate = Long.valueOf(originDate.trim());
				targetDate = new Date(longDate);
			} catch (NumberFormatException e) {
				try {
					targetDate = DateUtils.parseDate(originDate, DateDeserializer.pattern);
				} catch (ParseException pe) {
					//日期时间戳
					if (originDate.matches("^\\d+$")) {
						return new Date(Long.valueOf(originDate));
					}

					throw new IOException(String.format(
							"'%s' can not convert to type 'java.util.Date',just support timestamp(type of long) and following date format(%s)",
							originDate,
							StringUtils.join(pattern, ",")));
				}
			}
		}
		return targetDate;
	}

	@Override
	public Class<?> handledType() {
		return Date.class;
	}
}

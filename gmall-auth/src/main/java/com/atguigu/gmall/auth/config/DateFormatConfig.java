//package com.atguigu.gmall.auth.config;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.*;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
///**
// * @Description TODO  全局日期序列化
// * @Date 2020/9/6 20:53
// * @Created by yuant
// */
//@Configuration
//public class DateFormatConfig {
//
//	@Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
//	private String pattern;
//
//	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//	@Bean
//	@Primary
//	public ObjectMapper serializingObjectMapper() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		JavaTimeModule javaTimeModule = new JavaTimeModule();
//		javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
//		javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
//		objectMapper.registerModule(javaTimeModule);
//		return objectMapper;
//	}
//
//	/**
//	 * @description Date 时间类型装换
//	 */
//	@Component
//	public class DateSerializer extends JsonSerializer<Date> {
//		@Override
//		public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
//			String formattedDate = dateFormat.format(date);
//			gen.writeString(formattedDate);
//		}
//	}
//
//	/**
//	 * @description Date 时间类型装换
//	 */
//	@Component
//	public class DateDeserializer extends JsonDeserializer<Date> {
//
//		@Override
//		public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//			try {
//				return dateFormat.parse(jsonParser.getValueAsString());
//			} catch (ParseException e) {
//				throw new RuntimeException("Could not parse date", e);
//			}
//		}
//	}
//
//	/**
//	 * @description LocalDate 时间类型装换
//	 */
//	public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
//		@Override
//		public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//			gen.writeString(value.format(DateTimeFormatter.ofPattern(pattern)));
//		}
//	}
//
//	/**
//	 * @description LocalDate 时间类型装换
//	 */
//	public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
//		@Override
//		public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
//			return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(pattern));
//		}
//	}
//
//}

package com.atguigu.gmall.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 所有验证码实体类的父类
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateCode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String code;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	protected LocalDateTime expireTime;

	public ValidateCode() {}
	
	public ValidateCode(String code, int expireIn) {
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}

	public boolean isExpired() {
		return expireTime.isBefore(LocalDateTime.now());
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		return "ValidateCode [code=" + code + ", expireTime=" + expireTime + "]";
	}

	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now.toString());
		LocalDateTime localDateTime = now.plusSeconds(60);
		System.out.println(localDateTime.toString());
	}
}

package com.atguigu.gmall.auth.vo;

import com.atguigu.gmall.auth.config.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2020/9/6 23:13
 * @Created by yuant
 */
@Getter
@Setter
public class TestDateVo {

	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date time;

//	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JsonSerialize(using = DateSerializer.class)
	private Date time2;


	@Override
	public String toString() {
		return "TestDateVo{" +
				"time=" + time +
				", time2=" + time2 +
				'}';
	}
}

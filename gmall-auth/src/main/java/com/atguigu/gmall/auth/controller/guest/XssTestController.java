package com.atguigu.gmall.auth.controller.guest;

import com.alibaba.nacos.client.naming.utils.ThreadLocalRandom;
import com.atguigu.gmall.auth.vo.TestDateVo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @Description TODO
 * @Date 2020/8/29 12:00
 * @Created by yuant
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/guest")
public class XssTestController {

	@GetMapping("/get")
	public Object xssTest(@RequestParam String s){
		return s;
	}

	@GetMapping("/getObj")
	public Object getObj(@RequestParam Map s){
		return s;
	}

	@PostMapping("/postObj")
	public Object postObj(TestModel model){
		return model;
	}

	@PostMapping("/postJson")
	public Object postJson(@RequestBody TestModel model){
		return model;
	}

	@PostMapping("/time")
	public Object testTime(@RequestBody TestDateVo testDateVo){
		log.info("输出:{}", testDateVo);
		return testDateVo;
	}

	@Getter
	@Setter
	static class TestModel{
		private Integer id;
		private String name;
		private Date date;
	}


}

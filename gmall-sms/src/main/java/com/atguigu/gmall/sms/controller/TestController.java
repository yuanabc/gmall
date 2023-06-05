package com.atguigu.gmall.sms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2020/9/7 19:28
 * @Created by yuant
 */
//@RequestMapping("/t")
@RestController
public class TestController {

	@GetMapping("/test")
	public Object test(@RequestParam String id){
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("name","张三");
		return map;
	}

}

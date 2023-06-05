package com.atguigu.gmall.ssoserver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @Description TODO
 * @Date 2020/12/8 21:53
 * @Created by yuant
 */
@RestController
public class LoginController {

	@PostMapping("/dologin")
	public Object dologin(Map<String, Object> map){

		Set<String> strings = map.keySet();

		strings.stream().forEach(System.out::println);

		return map;
	}

}

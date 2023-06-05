package com.atguigu.gmall.ssoclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Date 2020/12/8 21:25
 * @Created by yuant
 */
@Slf4j
@Controller
public class HelloController {

	/**
	 * 无需登录就可访问
	 * @return
	 */
	@GetMapping("/hello")
	@ResponseBody
	public String hello(){
		return "hello";
	}


	@GetMapping("/employees")
	public String employees(Model model, HttpSession session){
		Object loginUser = session.getAttribute("loginUser");
		if(loginUser == null){
			return "redirect:http://ssoserver.com/login.html";
		}

		List<String> list = new ArrayList<>();
		list.add("A1");
		list.add("A2");
		list.add("A3");
		list.add("A4");
		model.addAttribute("emps", list);
		return "list";
	}

}

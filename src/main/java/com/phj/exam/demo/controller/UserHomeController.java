package com.phj.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
public class UserHomeController {
	@RequestMapping("/user/home/main")
	@ResponseBody
	public int showMain() {
		return 0;
	}
//	@RequestMapping("/user/home/main1")
//	@ResponseBody
//	public int showMain2() {
//		return 1;
//	}
//	@RequestMapping("/user/home/main2")
//	@ResponseBody
//	public int showMain3() {
//		return 2;
//	}
//	@RequestMapping("/user/home/main3")
//	@ResponseBody
//	public int showMain4() {
//		return 3;
//	}
//	
	
}

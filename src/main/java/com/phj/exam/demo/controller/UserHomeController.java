package com.phj.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
public class UserHomeController {
	@RequestMapping("/user/home/main")
	@ResponseBody
	public String getString() {

		return  "Main 입니다.";
	}
}

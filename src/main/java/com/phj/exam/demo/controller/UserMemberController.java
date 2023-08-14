package com.phj.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phj.exam.demo.service.MemberService;

@Controller 
public class UserMemberController {
	private MemberService memberService;
	
	public UserMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String nickName, String cellphoneNo, String email) {
		memberService.join(loginId, loginPw, nickName, cellphoneNo, email);
		return  "성공.";
	}
}

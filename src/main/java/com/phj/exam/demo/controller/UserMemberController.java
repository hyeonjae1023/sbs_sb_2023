package com.phj.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phj.exam.demo.service.MemberService;
import com.phj.exam.demo.vo.Member;

@Controller 
public class UserMemberController {
	private MemberService memberService;
	
	public UserMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickName, String cellphoneNo, String email) {
		if(loginId == null || loginId.trim().length() == 0) {
			return "loginId를 입력해 주세요.";
		}
		if(loginPw == null || loginPw.trim().length() == 0) {
			return "loginPw를 입력해 주세요.";
		}
		if(name == null || name.trim().length() == 0) {
			return "name를 입력해 주세요.";
		}
		if(nickName == null || nickName.trim().length() == 0) {
			return "nickName를 입력해 주세요.";
		}
		if(cellphoneNo == null || cellphoneNo.trim().length() == 0) {
			return "cellphoneNo를 입력해 주세요.";
		}
		if(email == null || email.trim().length() == 0) {
			return "email를 입력해 주세요.";
		}
		
		int id = memberService.join(loginId, loginPw, name, nickName, cellphoneNo, email);
		
		if(id == -1) {
			return "해당 아이디는 이용 중입니다.";
			
		}
		Member member = memberService.getMemberById(id);
		return member;
	}
}

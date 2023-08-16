package com.phj.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phj.exam.demo.service.MemberService;
import com.phj.exam.demo.util.Ut;
import com.phj.exam.demo.vo.Member;
import com.phj.exam.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

@Controller 
public class UserMemberController {
	private MemberService memberService;
	
	public UserMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickName, String cellphoneNo, String email) {
		if(Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력하세요.");
		}
		if(Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력하세요.");
		}
		if(Ut.empty(name)) {
			return ResultData.from("F-3", "이름을 입력하세요.");
		}
		if(Ut.empty(nickName)) {
			return ResultData.from("F-4", "닉네임을 입력하세요.");
		}
		if(Ut.empty(cellphoneNo)) {
			return ResultData.from("F-5", "전화번호을 입력하세요.");
		}
		if(Ut.empty(email)) {
			return ResultData.from("F-6", "이메일을 입력하세요.");
		}
		
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickName, cellphoneNo, email);

		if ( joinRd.isFail() ) {
			return (ResultData) joinRd;
		}
		
	Member member = memberService.getMemberById(joinRd.getData1());
		
		return ResultData.newData(joinRd, member);
	}
	
	@RequestMapping("/user/member/doLogin")
	@ResponseBody
	public ResultData<Member> doLogin(HttpSession httpSession, String loginId, String loginPw) {
		boolean isLogined = false;
		
		if(httpSession.getAttribute("loginedMember") != null) {
			isLogined = true;
		}
		
		if(isLogined) {
			return ResultData.from("F-5","이미 로그인 되어 있습니다.");
		}
		if(Ut.empty(loginId)) {
			return ResultData.from("F-1","로그인 아이디 입력하세요.");
		}
		if(Ut.empty(loginPw)) {
			return ResultData.from("F-2","로그인 비번 입력하세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) {
			return ResultData.from("F-3","해당 회원이 존재하지 않습니다.");
		}
		if(member.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-4", "비밀번호가 일치하지 않습니다.");
		}
		
		httpSession.setAttribute("loginedMemberId", member.getId());
		
		return ResultData.from("S-1",Ut.f("%s님 환영합니다.",member.getNickName()));
	}
	
	@RequestMapping("/user/member/doLogout")
	@ResponseBody
	public ResultData<Member> doLogout(HttpSession httpSession) {
		boolean isLogined = false;
		
		if(httpSession.getAttribute("loginedMemberId") == null) {
			isLogined = true;
		}
		
		if(isLogined) {
			return ResultData.from("F-1","이미 로그아웃 되었습니다.");
		}
		
		httpSession.removeAttribute("loginedMemberId");
		
		return ResultData.from("S-1","로그아웃 되었습니다.");
	}
}

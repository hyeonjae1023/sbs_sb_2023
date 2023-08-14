package com.phj.exam.demo.service;

import org.springframework.stereotype.Service;

import com.phj.exam.demo.repository.MemberRepository;
import com.phj.exam.demo.vo.Member;

@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int join(String loginId, String loginPw,String name, String nickName, String cellphoneNo, String email) {
		memberRepository.join(loginId, loginPw, name, nickName, cellphoneNo, email);
		return memberRepository.getLastMemberId();

	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}

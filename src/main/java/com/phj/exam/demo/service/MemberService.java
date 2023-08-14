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
		Member oldMember = getMemberByLoginId(loginId);
		
		if(oldMember != null) {
			return -1;
		}
		
		memberRepository.join(loginId, loginPw, name, nickName, cellphoneNo, email);
		return memberRepository.getLastMemberId();

	}

	private Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}

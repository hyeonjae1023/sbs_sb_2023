package com.phj.exam.demo.service;

import org.springframework.stereotype.Service;

import com.phj.exam.demo.repository.MemberRepository;
import com.phj.exam.demo.util.Ut;
import com.phj.exam.demo.vo.Member;
import com.phj.exam.demo.vo.ResultData;

@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public ResultData<Integer> join( String loginId, String loginPw,String name, String nickName, String cellphoneNo, String email) {
		Member oldMember = getMemberByLoginId(loginId);
		//로그인 아이디 중복체크
		if(oldMember != null) {
			return ResultData.from("F-7",Ut.f("아이디(%s)가 이미 이용 중입니다.", loginId));
		}
		
		oldMember = getMemberByNameAndEmail(name, email);
		
		if(oldMember != null) {
			return ResultData.from("F-8",Ut.f("이름(%s),이메일(%s)가 이미 이용 중입니다.", name,email));
		}
		memberRepository.join(loginId, loginPw, name, nickName, cellphoneNo, email);
		
		int id = memberRepository.getLastMemberId();
		return ResultData.from("S-1","회원가입 완료",id);

	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}

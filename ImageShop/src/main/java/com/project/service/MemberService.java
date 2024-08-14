package com.project.service;

import java.util.List;

import com.project.domain.Member;

public interface MemberService {
	//등록 처리
	public void register(Member member) throws Exception;

	public List<Member> list() throws Exception;

	public Member read(int userNo) throws Exception;

	public void modify(Member member) throws Exception;

	public void remove(int userNo) throws Exception;

	public void setupAdmin(Member member) throws Exception;

	public int countAll() throws Exception;

	public int getCoin(int userNo) throws Exception;

}

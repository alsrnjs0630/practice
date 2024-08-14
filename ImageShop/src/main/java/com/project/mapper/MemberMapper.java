package com.project.mapper;

import java.util.List;

import com.project.domain.Member;
import com.project.domain.MemberAuth;

public interface MemberMapper {
	// 등록 처리
	public void create(Member member) throws Exception;
	// 권한 생성
	public void createAuth(MemberAuth memberAuth) throws Exception;
	public List<Member> list() throws Exception;
	public Member read(int userNo) throws Exception;
	public void update(Member member) throws Exception;
	public void modifyAuth(MemberAuth memberAuth) throws Exception;
	public void deleteAuth(int userNo) throws Exception;
	public void delete(int userNo) throws Exception;
	public int countAll() throws Exception;
	public Member readByUserId(String userId);
	

}

package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.project.domain.UserItem;

public interface UserItemMapper {
	// 구매 상품 등록 처리
	public void create(UserItem userItem) throws Exception;
	// 구매 상품 상세 페이지
	public UserItem read(Integer userItemNo) throws Exception;
	// 구매 상품 목록 페이지
	public List<UserItem> list(@Param("userNo") Integer userNo) throws Exception;
}

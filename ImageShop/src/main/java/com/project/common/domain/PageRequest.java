package com.project.common.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageRequest {
	private int page;			// 현재 페이지 번호
	private int sizePerPage;	// 페이지당 항목 수
	
	// 검색 유형과 검색어를 멤버변수(필드)로 선언
	private String searchType;
	private String keyword;
	
	// 기본 생성자
	public PageRequest() {
		this.page = 1;				// 기본 페이지 번호 1번으로 설정
		this.sizePerPage = 10;		// 기본 페이지당 항목 수를 10개로 설정
	}
	
	// 페이지 번호 설정 (0 이하로 설정 시 기본값 1로 설정)
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	
	// 페이지당 항목 수 설정 (0 이하 또는 100 초과로 설정 시 기본값 10으로 설정)
	public void setSizePerPage(int size) {
		if (size <= 0 || size > 100) {
			this.sizePerPage = 10;
			return;
		}
		this.sizePerPage = size;
	}
	
	public int getPage() {
		return page;
	}
	
	// 마이바티스 SQL 매퍼를 위한 메서드
	// 데이터베이스 쿼리에서 시작 인덱스 계산
	public int getPageStart() {
		return(this.page -1) * sizePerPage; // (현제 페이지 -1) * 페이지당 항목 수 
	}
	
	// 마이바티스 SQL 매퍼를 위한 메서드
	public int getSizePerPage() {
		return this.sizePerPage;
	}
	
	// 검색 유형과 검색어를 멤버변수의 Getter/Setter 메서드
	public String getSearchType() {
		return searchType;
	}
	
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	// 멤버 변수를 활용하여 다양한 형태의 쿼리파라미터를 생성한다.
	public String toUriString() {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("page", this.page)
				.queryParam("size", this.sizePerPage)
				.queryParam("searchType", this.searchType)
				.queryParam("keyword", this.keyword)
				.build();
		
		return uriComponents.toUriString();		// 페이지 요청을 위한 쿼리 문자열 생성
	}
	
	public String toUriString(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("page", this.page)
				.queryParam("size", this.sizePerPage)
				.queryParam("searchType", this.searchType)
				.queryParam("keyword", this.keyword)
				.build();
		
		return uriComponents.toUriString();		// 페이지 요청을 위한 쿼리 문자열 생성
	}
	
	public String toUriStringByPage(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("page", this.page)
				.queryParam("size", this.sizePerPage)
				.build();
		
		return uriComponents.toUriString();		// 페이지 요청을 위한 쿼리 문자열 생성
	}
}

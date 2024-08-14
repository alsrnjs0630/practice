package com.project.service;

import java.util.List;

import com.project.domain.CodeGroup;

public interface CodeGroupService {
	//등록처리
	public void register(CodeGroup codeGroup) throws Exception;
	//목록페이지
	public List<CodeGroup> list() throws Exception;
	//상세수정페이지
	public CodeGroup read(String groupCode) throws Exception;
	//수정처리
	public void modify(CodeGroup codeGroup) throws Exception;
	
	public void remove(String codeGroup) throws Exception;
	
}

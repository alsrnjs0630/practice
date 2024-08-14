package com.project.mapper;

import java.util.List;

import com.project.domain.CodeGroup;

public interface CodeGroupMapper {
	//등록처리
	public void create(CodeGroup codeGroup) throws Exception;

	public List<CodeGroup> list() throws Exception;

	public CodeGroup read(String groupCode) throws Exception;
	
	public void update(CodeGroup codeGroup) throws Exception;
	
	public void remove(String codeGroup) throws Exception;

	public void delete(String codeGroup) throws Exception;
}

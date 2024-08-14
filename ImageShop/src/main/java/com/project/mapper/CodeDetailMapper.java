package com.project.mapper;

import java.util.List;

import com.project.domain.CodeDetail;

public interface CodeDetailMapper {

	public Integer getMaxSortSeq(String groupCode) throws Exception;

	public void create(CodeDetail codeDetail) throws Exception;

	public List<CodeDetail> list() throws Exception;

	public CodeDetail read(CodeDetail codeDetail) throws Exception;
	
	public void update(CodeDetail codeDetail) throws Exception;
	
	public void delete(CodeDetail codeDetail) throws Exception;
}

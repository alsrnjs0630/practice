package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.CodeGroup;
import com.project.mapper.CodeGroupMapper;

@Service
public class CodeGroupServiceImpl implements CodeGroupService {
	
	@Autowired
	private CodeGroupMapper mapper;

	@Override
	public void register(CodeGroup codeGroup) throws Exception {
		mapper.create(codeGroup);
	}

	@Override
	public List<CodeGroup> list() throws Exception {
		return mapper.list();
	}

	@Override
	public CodeGroup read(String groupCode) throws Exception {
		return mapper.read(groupCode);
	}
	@Override
	public void modify(CodeGroup codeGroup) throws Exception{
		mapper.update(codeGroup);
	}

	@Override
	public void remove(String codeGroup) throws Exception {
		mapper.delete(codeGroup);
	}
}

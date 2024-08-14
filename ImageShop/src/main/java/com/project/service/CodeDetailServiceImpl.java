package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.CodeDetail;
import com.project.mapper.CodeDetailMapper;

@Service
public class CodeDetailServiceImpl implements CodeDetailService {
	
	@Autowired
	private CodeDetailMapper mapper;

	@Override
	public void register(CodeDetail codeDetail) throws Exception {
		String groupCode = codeDetail.getGroupCode();
		int maxSortSeq = mapper.getMaxSortSeq(groupCode);
		
		codeDetail.setSortSeq(maxSortSeq + 1);
		
		mapper.create(codeDetail);
	}

	@Override
	public List<CodeDetail> list() throws Exception {
		return mapper.list();
	}

	@Override
	public CodeDetail read(CodeDetail codeDetail) throws Exception {
		return mapper.read(codeDetail);
	}

	@Override
	public void modify(CodeDetail codeDetail) throws Exception {
		mapper.update(codeDetail);
		
	}

	@Override
	public void remove(CodeDetail codeDetail) throws Exception {
		mapper.delete(codeDetail);
		
	}
}

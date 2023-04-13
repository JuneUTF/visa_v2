package com.bteam.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bteam.mapper.MemberMapper;
import com.bteam.model.MemberModel;
import com.bteam.service.MemberService;
@Service
public class MemberImpl implements MemberService{
	@Resource
	MemberMapper mapper;
	@Override
	public List<MemberModel>memberSelectUsername (String username){
			return mapper.memberSelectUsername(username);
		};
}

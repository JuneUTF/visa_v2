package com.bteam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bteam.model.MemberModel;
@Service
public interface MemberService {
	List<MemberModel> memberSelectUsername(String username);
}

package com.bteam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bteam.model.LoginModel;
@Service
public interface LoginService {
	List<LoginModel> login(String username);
	List<LoginModel> loginrole(String username);
	List<LoginModel> selectCheckBlock (String username);
	int setCheckBlock(String username);
}

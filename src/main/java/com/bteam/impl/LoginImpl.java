package com.bteam.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bteam.mapper.LoginMapper;
import com.bteam.model.LoginModel;
import com.bteam.service.LoginService;
@Service
public class LoginImpl implements LoginService{
	@Resource
	LoginMapper mapper;
	@Override
	public List<LoginModel>login (String username){
			return mapper.login(username);
		};
		public List<LoginModel>loginrole (String username){
			return mapper.loginrole(username);
		}
		public List<LoginModel>selectCheckBlock (String username){
			return mapper.selectCheckBlock(username);
		}
		public int setCheckBlock(String username) {
			return mapper.setCheckBlock(username);
		}
}

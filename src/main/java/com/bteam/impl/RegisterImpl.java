package com.bteam.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bteam.mapper.RegisterMapper;
import com.bteam.model.RegisterModel;
import com.bteam.service.RegisterService;
@Service
public class RegisterImpl implements RegisterService {
@Resource
RegisterMapper mapper;
@Override
		public List<RegisterModel> checkuser(RegisterModel userRegisterModel) {
				return mapper.checkuser(userRegisterModel);
		}
		public int registerUser(RegisterModel userRegisterModel) {
				return mapper.registerUser(userRegisterModel);
		}
		public int registerAdmin(RegisterModel userRegisterModel) {
			return mapper.registerAdmin(userRegisterModel);
	}
}

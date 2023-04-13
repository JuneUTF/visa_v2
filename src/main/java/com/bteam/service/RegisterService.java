package com.bteam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bteam.model.RegisterModel;

@Service
public interface RegisterService {
	  List<RegisterModel> checkuser(RegisterModel userRegisterModel);
	 int registerUser(RegisterModel userRegisterModel);
	 int  registerAdmin(RegisterModel userRegisterModel);
}

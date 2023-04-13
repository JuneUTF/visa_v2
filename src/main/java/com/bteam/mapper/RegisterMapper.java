package com.bteam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bteam.model.RegisterModel;

@Repository
@Mapper
public interface RegisterMapper {
		 List<RegisterModel> checkuser(RegisterModel userRegisterModel);
		 int registerAdmin(RegisterModel userRegisterModel);
		 int  registerUser(RegisterModel userRegisterModel);
}

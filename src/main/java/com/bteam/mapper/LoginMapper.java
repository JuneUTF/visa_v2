package com.bteam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bteam.model.LoginModel;

	@Repository
	@Mapper
	public interface LoginMapper {
	List<LoginModel> login (String username);
	List<LoginModel> loginrole (String username);
	List<LoginModel> selectCheckBlock (String username);
	int setCheckBlock(String username);
}

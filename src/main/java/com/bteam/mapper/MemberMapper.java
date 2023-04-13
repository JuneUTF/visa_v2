package com.bteam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bteam.model.MemberModel;

	@Repository
	@Mapper
	public interface MemberMapper {
	List<MemberModel> memberSelectUsername (String username);
}

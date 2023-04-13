package com.bteam.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bteam.model.MemberModel;
import com.bteam.service.MemberService;

@Controller
public class MemberController {
	@Resource
	MemberService memberService;
//	ログインしたのユーザー名をとり
	@GetMapping("/mypage")
	public String showUser(MemberModel memberModel) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginuser = authentication.getName();
		if(loginuser.equals("anonymousUser")) {
			return "redirect:/login";	
		}else {
			
		
		List<MemberModel> user = memberService.memberSelectUsername(loginuser);
		System.out.println(user);
		}
//		Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
//		String rolesStr = String.join(",", roles.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
//		System.out.println(rolesStr);
//		System.out.println(loginuser);
		return "member/showuser";
	}
}

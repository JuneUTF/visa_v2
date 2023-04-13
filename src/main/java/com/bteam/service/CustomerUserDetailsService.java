package com.bteam.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bteam.model.LoginModel;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
//	　loginService　読み込む
	@Resource
     LoginService loginService;
	@Override
//	データベースにユーザー名入力したデータを読み込む
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		ユーザー名とパスワードがUserテーブルで読み込む
		List<LoginModel> listUser = loginService.login(username);
//		レーベルがRoleテーブルで読み込む
		List<LoginModel> listRole = loginService.loginrole(username);
//		入力したユーザー名がない
		if(listUser.isEmpty()) {
//			ユーザー名とパスワードがが間違い時はエラー表示
		 throw new UsernameNotFoundException("User not found with username: " + username);
		}else {
//			ユーザー名が　正しく
//			listUser index 0 設定
		LoginModel user= listUser.get(0);
		if(listRole.size()==0) {
//			管理者ではない時は一般的な人設定
			user.setRole("USER");
		}else {
//			管理者としてuserの中に設定
			user.setRole(listRole.get(0).getRole());
		}
//		管理者設定
		List<GrantedAuthority> grantList = new ArrayList<>();
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
		grantList.add(authority);
//		データを送ります。
		return new User(user.getUsername(), user.getPassword(), grantList);
			}
		}

}

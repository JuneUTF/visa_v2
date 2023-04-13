package com.bteam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bteam.service.CustomerUserDetailsService;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
//CustomerUserDetailsService　読み込む（データチェック送りデータを読み込）
	@Autowired
	CustomerUserDetailsService userDetailsService;
	@Bean
//	PasswordEncoder　変更クラス作成
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}
	@Override

	//ユーザー名とパスワードをチェックします。
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
//		入力したパスワードをBCrypt型として変更します
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
//	login th
	
	
	
	
	@Override
//	URL　設定
	 protected void configure(final HttpSecurity http) throws Exception {
//		この脆弱性を突いた攻撃をそのままCSRF攻撃と呼びます。
		http.csrf().disable();
//		("/", "/login", "/logout"　誰でもできます
		http.authorizeRequests().antMatchers("/**").permitAll();
		http.authorizeRequests()
//		../admin/**　は管理者だけはいります
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasAnyRole("ADMIN","MENBER")
//		ユーザーページはログイン後に誰でも
        .antMatchers("/member/**","/delete/**","/update/**").hasAnyRole("USER","ADMIN","MENBER");
//　　間違った場合はaboutに移動します
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/about");
//		ログイン設定
		http.authorizeRequests().and()	
					.formLogin()
//					action 設定
					.loginProcessingUrl("/login") 
//					ログインページ表示設定
					.loginPage("/login")
//					ログインOKは移動するページ設定
					.defaultSuccessUrl("/user")
//					ログイン失敗ページ設定
					.failureHandler((request, response, exception) -> {
	                    response.sendRedirect("/login?error");
	                })
//					ログインページの中にネームデータ取り出し
					.failureUrl("/login?error=true")
//					ログインページの中にネームデータ取り出し
					.usernameParameter("username")
					.passwordParameter("password")
					.and()
//					ログアウトURL設定
					.logout().logoutUrl("/logout")
//					ログアウトOKのページ設定
					.logoutSuccessUrl("/");
		

	}
}

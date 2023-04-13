package com.bteam.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginModel {
	@NotEmpty(message = "名前を入力してください")
    @Size(min=1,max = 50 , message = "名前は255桁以内で入力してください")
	private String username;
	@NotEmpty(message = "パスワードを入力してください")
    @Size(min=1,max =  16, message = "パスワードは8～16桁以内で入力してください")
	private String password;
	private String role;
	private String note;
	private int logintimes;
}

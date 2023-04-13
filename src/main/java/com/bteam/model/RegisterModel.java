package com.bteam.model;


import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterModel {
	@NotEmpty(message = "ユーザー名を入力してください")
    @Size(max = 50 , message = "ユーザー名は6～50桁以内で入力してください")
	@Size(min=6, message = "ユーザー名は6～50桁以内で入力してください")
	private String username;
	
	
	@NotEmpty(message = "パスワードを入力してください")
    @Size(max =16, message = "パスワードは8～16桁以内で入力してください")
    @Size(min=8, message = "パスワードは8～16桁以内で入力してください")
	private String password;
	
	
	@NotEmpty(message = "在留カード番号を入力してください")
    @Size(max =  12, message = "名前は12桁以内で入力してください")
	private String number;
	
	
	@NotEmpty(message = "名前を入力してください")
    @Size(max =  255, message = "名前は255桁以内で入力してください")
	private String name;
	
	
	@NotEmpty(message = "性別を選択してください")
	private String sex;
	
	
	@NotEmpty(message = "国籍を入力してください")
    @Size(max=12, message = "国籍は12桁以内で入力してください")
	private String country;
	
	
	@NotEmpty(message = "住所を入力してください")
    @Size(max=255, message = "住所は255桁以内で入力してください")
	private String address;
	
	
	private String birthday;
	@NotEmpty(message = "ビザ期限を入力してください")
	private String visaDay;
	private Date visa;
	
	
	@NotEmpty(message = "在留資格を入力してください")
	private String visatype;

	private String avatar;
	private String visadate;
	private String note;
	private int logintimes;
}

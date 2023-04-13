package com.bteam.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bteam.model.RegisterModel;
import com.bteam.service.RegisterService;


@Controller
public class LoginRegisterController {
	@Resource
	RegisterService registerService;
@GetMapping("/login")
public String showLogin() {

	return "login";
}

//USER　PENDING　登録
@GetMapping("/register")
public String showRegister() {
	return "register";
}
@PostMapping("/register")
public String register(@Validated @ModelAttribute RegisterModel registerModel, BindingResult result, Model model,@RequestParam("file") MultipartFile file) {
//	エラーチェック
	 if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
//            	エラー内容を追加
                errorList.add(error.getDefaultMessage());
            }
//            エラー一名を表示します
            model.addAttribute("warning", errorList.get(0));
            return "register";
        }else {
        	String fileName="";
        	String lastFile ="";
//     	写真ファイルがあるか？　チャック
        	 if (file.isEmpty()) {
//        		 ない場合は　”user.png”に設定
        		 fileName+="user.png";
        		 lastFile+=".png";
        	 }else {
//        		 写真ある場合
//        	拡張子　のとり
        	 lastFile += file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        	
//	写真を変更
        	 fileName += registerModel.getUsername()+ lastFile;
        	}
        	registerModel.setAvatar(fileName);
//        	file サイズ　とり
        	long sizeFile = file.getSize();
//        	ファイルは写真ですか？チャックします。
        	if(lastFile.equals(".jpg") || lastFile.equals(".jpeg") || lastFile.equals(".png") ){
//        		写真のサイズをチャックします　2097152は2097152byte　＝　２Mb
        		if(sizeFile <=2097152) {
//        			ユーザー名はあるか？ないか？チャックします
        				List<RegisterModel> check = registerService.checkuser(registerModel);
//        				ない場合
        				if(check.size()==0) {
//        		 					入力したビザ期限をString型からDateSQL型を変更
    								String inputDate = registerModel.getVisaDay();
    										try {
    											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    											java.util.Date date = sdf.parse(inputDate);
    											Date sqlDate = new Date(date.getTime());
//        		 							変更したビザ期限をuserRegisterModelに設定
    											registerModel.setVisa(sqlDate);
    										} catch (Exception e) {
//        		 					変更出来ないは登録できませんとエラー表示
    											model.addAttribute("warning", "登録が失敗しました。");
    											return "register";
    										}	
//        		 					パスワードを変更
    										registerModel.setPassword(new BCryptPasswordEncoder().encode(registerModel.getPassword()));
//    										現在の時間取り
    										LocalDateTime currentDateTime = LocalDateTime.now();
//    										ノート設定
    										registerModel.setNote(currentDateTime.toString()+":"+registerModel.getUsername()+"を登録します。");
//        		 				データベースをinsert
    										int cont = registerService.registerUser(registerModel);
//        		 				insertをチェック
    										if(cont ==1) {
//        		 					insert　OKは表示する画面を移動します
    											try { 
//    												ファイルをフォルダに移動します。
    			    								file.transferTo(new File("C:\\Users\\juneu\\OneDrive\\Máy tính\\K&K\\project1\\B_Visa_Team_V2\\src\\main\\resources\\static\\photos\\" + fileName));
    			    								}
        										catch (IOException e) {
//        											ファイルを移動できません
        											return "register";
        										}
//    											登録できますー＞登録Usernameのマイページを移動
    											return "redirect:/menber/"+registerModel.getUsername();
    										}else {
//    											登録できない場合
    											return "register";
    										}
        				}else {
//        		 			ユーザー名がある時は登録出来ません
        					model.addAttribute("warning", "ユーザー名が存在しました。");
        		 			return "register";
						}
        			}else {
//        				ファイルのサイズは大きすぎるのエラー
        				model.addAttribute("warning", "ファイルのサイズは２Mbまでです");
        				return "register";
        			}
        		}else {
//        			ファイルの拡張子が正しくないのエラー
        			model.addAttribute("warning", "ファイルの拡張子はjpg、jpeg、pngです");
        			return "register";
				}
        }

       }


//				管理者として登録
@GetMapping("/user/register")
public String adminRegister() {
	return "register";
}
@PostMapping("/user/register")
public String userregister(@Validated @ModelAttribute RegisterModel registerModel, BindingResult result, Model model,@RequestParam("file") MultipartFile file) {
//	ログインしたのユーザー名をとり
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	String username = authentication.getName();
	//	エラーチェック
	 if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
//            	エラー内容を追加
                errorList.add(error.getDefaultMessage());
            }
            System.out.println(errorList);
//            エラー一名を表示します
            model.addAttribute("warning", errorList.get(0));
            return "register";
        }else {
        	String fileName="";
        	String lastFile ="";
//     	写真ファイルがあるか？　チャック
        	 if (file.isEmpty()) {
//        		 ない場合は　”user.png”に設定
        		 fileName+="user.png";
        		 lastFile+=".png";
        	 }else {
//        		 写真ある場合
//        	拡張子　のとり
        	 lastFile += file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        	
//	写真を変更
        	 fileName += registerModel.getUsername()+ lastFile;
        	}
        	registerModel.setAvatar(fileName);
//        	file サイズ　とり
        	long sizeFile = file.getSize();
//        	ファイルは写真ですか？チャックします。
        	if(lastFile.equals(".jpg") || lastFile.equals(".jpeg") || lastFile.equals(".png") ){
//        		写真のサイズをチャックします　2097152は2097152byte　＝　２Mb
        		if(sizeFile <=2097152) {
//        			ユーザー名はあるか？ないか？チャックします
        				List<RegisterModel> check = registerService.checkuser(registerModel);
//        				ない場合
        				if(check.size()==0) {
//        		 					入力したビザ期限をString型からDateSQL型を変更
    								String inputDate = registerModel.getVisaDay();
    										try {
    											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    											java.util.Date date = sdf.parse(inputDate);
    											Date sqlDate = new Date(date.getTime());
//        		 							変更したビザ期限をuserRegisterModelに設定
    											registerModel.setVisa(sqlDate);
    										} catch (Exception e) {
//        		 					変更出来ないは登録できませんとエラー表示
    											model.addAttribute("warning", "登録が失敗しました。");
    											return "register";
    										}	
//        		 					パスワードを変更
    										registerModel.setPassword(new BCryptPasswordEncoder().encode(registerModel.getPassword()));
//        		 				データベースをinsert
//    										現在の時間取り
    										LocalDateTime currentDateTime = LocalDateTime.now();
//    										ノート設定
    										registerModel.setNote(currentDateTime.toString()+":"+username+"は"+registerModel.getUsername()+"を登録します。");
    										int cont = registerService.registerAdmin(registerModel);
//        		 				insertをチェック
    										if(cont ==1) {
//        		 					insert　OKは表示する画面を移動します
    											try { 
//    												ファイルをフォルダに移動します。
    			    								file.transferTo(new File("C:\\Users\\juneu\\OneDrive\\Máy tính\\K&K\\project1\\B_Visa_Team_V2\\src\\main\\resources\\static\\photos\\" + fileName));
    			    								}
        										catch (IOException e) {
//        											ファイルを移動できません
        											return "register";
        										}
//    											登録できますー＞登録Usernameのマイページを移動
    											return "redirect:/menber/"+registerModel.getUsername();
    										}else {
//    											登録できない場合
    											return "register";
    										}
        				}else {
//        		 			ユーザー名がある時は登録出来ません
        					model.addAttribute("warning", "ユーザー名が存在しました。");
        		 			return "register";
						}
        			}else {
//        				ファイルのサイズは大きすぎるのエラー
        				model.addAttribute("warning", "ファイルのサイズは２Mbまでです");
        				return "register";
        			}
        		}else {
//        			ファイルの拡張子が正しくないのエラー
        			model.addAttribute("warning", "ファイルの拡張子はjpg、jpeg、pngです");
        			return "register";
				}
        }

       }
}
 
					

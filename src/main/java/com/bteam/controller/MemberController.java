package com.bteam.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bteam.model.MemberModel;
import com.bteam.service.DateConverter;
import com.bteam.service.MemberService;

@Controller
@RequestMapping("/mypage")
public class MemberController {
	@Resource
	MemberService memberService;
//	ログインしたのユーザー名をとり
	@GetMapping("")
	public String showUser(MemberModel memberModel,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginuser = authentication.getName();
		if(loginuser.equals("anonymousUser")) {
			return "redirect:/login";	
		}else {
			
		
		List<MemberModel> user = memberService.memberSelectUsername(loginuser);
		System.out.println(user);


		DateConverter changeDate = new DateConverter();
		user.forEach(e -> {
//    		changeDateの中にreamdateを取り出し ,何日をセットします
    		long remday = changeDate.reamdate(e.getVisadate());
//    		changeDateの中にchangedateを取り出し　、Listにセットします
//    		例　	 2023-05-21 変更　2023年05月21日
    		e.setVisadate(changeDate.changedate(e.getVisadate()));
    		e.setBirthday(changeDate.changedate(e.getBirthday()));
//    		text 
    		String outString = "";
//    		期限切れはColor=0
//    		本日に期限切れColor=1
//    		30日以内切れColor=2
//    		30日以上切れColor=３
    		
//    		remday=0本日に期限切れ
    	    		if(remday==0) {
    	    			outString+="本日に期限切れ";
    	    			e.setColor(1);
    	    		}else {

//    		    		LONG型　To　INT　型　とマイナス消し
    		    		int rem = (int) Math.abs(remday);
//    		    		JavaのPeriod　読み込
    		    		 LocalDate today = LocalDate.now();
    		    	     LocalDate futureDate = today.plusDays(rem);
    		    		Period period = Period.between(today, futureDate);
//    		    		何年計算
    		    		int years = period.getYears();
//    		    		何月計算
    		    		int months = period.getMonths();
//    		    		何日計算
    		    		int days = period.getDays();
//    		    		チェック
    		    		if(years>0) {
    		    			outString+=(years +"年");
    		    			e.setColor(3);
    		    		}
    		    		if(months>0) {
    		    			outString+=(months +"月");
    		    			e.setColor(3);
    		    		}
    		    		if(days>0) {
    		    			outString+=(days +"日");
    		    			e.setColor(2);
    		    		}
    		    		if(remday>0) {
    		    			outString+="前に切れ";
    		    			e.setColor(0);
    		    		}
    				}
//    	    		String　セットします
    	    		 e.setNote(outString);
    	});
		
		
		
		
		model.addAttribute("user", user);
		}
//		Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
//		String rolesStr = String.join(",", roles.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
//		System.out.println(rolesStr);
//		System.out.println(loginuser);
		return "member/mypage";
	}

	
//	update show 
	@GetMapping("/update")
	public String showUpdate(MemberModel memberModel,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginuser = authentication.getName();
		if(loginuser.equals("anonymousUser")) {
			return "redirect:/login";	
		}else {
		List<MemberModel> user = memberService.memberSelectUsername(loginuser);
		model.addAttribute("user", user);
		System.out.println(user);
		
		return "member/update";
		}
	}
	@PostMapping("/update")
	public String pdate() {
		System.out.println("ok");
		return "member/update";
	}
}

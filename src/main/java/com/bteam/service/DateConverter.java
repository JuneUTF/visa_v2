package com.bteam.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateConverter {
	// 2023-05-21 変更　2023年05月21日
    public  String changedate(String dateStr) {
//    	date String から LocalDate型に変更
        LocalDate date = LocalDate.parse(dateStr);
//        日本の年月日型変更
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        return date.format(formatter);
    }
    public long reamdate(String dateStr) {
//    	date String から LocalDate型に変更
    	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         LocalDate inputDate = LocalDate.parse(dateStr, formatter);
//         今日日付を取り出し
         LocalDate currentDate = LocalDate.now();
//			計算
         long days = currentDate.toEpochDay() - inputDate.toEpochDay();
    	return days;
    }
}


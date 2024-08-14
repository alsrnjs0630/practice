package com.project.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	//매핑으로 경로를 localhost8080로 설정 
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String home(Locale locale, Model model) {
		//현재날짜와 시간을 갖는 객체 생성
				Date date = new Date();
				//DateFormat.Long 날짜와 시간을 자세하게 표현하는 형식
				DateFormat dateFormat = 
		DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
				// 날짜와 시간을 특정 형식으로 문자열로 변환
				String formattedDate = dateFormat.format(date);
				//"serverTime": 뷰에서 데이터를 참조하는 데 사용하는 이름, 뷰 템플릿(home.jsp)에서 데이터로 사용
				//formattedDate: 전달하려는 데이터 formattedDate는 날짜와 시간이 포맷된 문자열
				model.addAttribute("serverTime", formattedDate);
				//home 경로에 해당하는 값을 주소창에 입력했을 때 home.jsp에 있는 내용을
				//홈페이지에 출력
		return "home";
	}
	
}

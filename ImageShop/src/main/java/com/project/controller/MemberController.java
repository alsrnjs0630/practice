package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.common.domain.CodeLabelValue;
import com.project.domain.Member;
import com.project.service.CodeService;
import com.project.service.MemberService;

import kotlin.contracts.Returns;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class MemberController {
	// 필드 주입 , 자동으로 객체 주입
	@Autowired
	private MemberService service;

	@Autowired
	private CodeService codeService;

	// 스프링 시큐리티의 비밀번호 암호처리기
	@Autowired
	private PasswordEncoder passwordEncoder;

	// void 메소드 이름과 URL 경로 + 간단한 뷰를 이용할때 사용
	// String 문자열을 통해 뷰의 이름이나 리다이렉션 경로 지정

	// 등록 페이지
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerForm(Member member, Model model) throws Exception {
		// 작업코드 목록을 조회하여 뷰에 전달
		String groupCode = "A00";
		List<CodeLabelValue> jobList = codeService.getCodeList(groupCode);

		model.addAttribute("jobList", jobList);
	}

	// 등록 처리
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Validated Member member, BindingResult result, Model model, RedirectAttributes rttr)
			throws Exception {
		if (result.hasErrors()) {
			// 직업 코드 목록을 조회하여 뷰에 전달
			String groupCode = "A00";
			List<CodeLabelValue> jobList = codeService.getCodeList(groupCode);

			model.addAttribute("jobList", jobList);

			return "user/register";
		}
		// 비밀번호 암호화
		String inputPassword = member.getUserPw();
		member.setUserPw(passwordEncoder.encode(inputPassword));

		service.register(member);

		rttr.addFlashAttribute("userName", member.getUserName());
		return "redirect:/user/registerSuccess";
	}

	// 등록 성공 페이지
	@RequestMapping(value = "/registerSuccess", method = RequestMethod.GET)
	public void registerSuccess(Model model) throws Exception {

	}

	// 회원 목록 페이지, 관리자 권한을 가진 사용자만 접근 가능
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void list(Model model) throws Exception {
		model.addAttribute("list", service.list());
	}

	// 상세 페이지
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(int userNo, Model model) throws Exception {
		// 직업 코드 목록을 조회하여 뷰에 전달
		String groupCode = "A00";
		List<CodeLabelValue> jobList = codeService.getCodeList(groupCode);

		model.addAttribute("jobList", jobList);
		model.addAttribute(service.read(userNo));
	}

	// 수정 페이지
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyForm(int userNo, Model model) throws Exception {
		// 직업 코드 목록을 조회하여 뷰에 전달
		String groupCode = "A00";
		List<CodeLabelValue> jobList = codeService.getCodeList(groupCode);

		model.addAttribute("jobList", jobList);
		model.addAttribute(service.read(userNo));
	}

	// 수정 처리
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(Member member, RedirectAttributes rttr) throws Exception {
		service.modify(member);

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/user/list";
	}

	// 회원 삭제 처리, 관리자 권한을 가진 사용자만 접근이 가능
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String remove(int userNo, RedirectAttributes rttr) throws Exception {
		service.remove(userNo);

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/user/list";
	}

	// 회원 테이블에 데이터가 없으면 최초 관리자를 생성한다..
	@RequestMapping(value = "/setup", method = RequestMethod.POST)
	public String setupAdmin(Member member, RedirectAttributes rttr) throws Exception {
		if (service.countAll() == 0) {
			String inputPassword = member.getUserPw();
			member.setUserPw(passwordEncoder.encode(inputPassword));
			
			member.setJob("00");
			
			service.setupAdmin(member);
			
			
			rttr.addFlashAttribute("userName", member.getUserName());
			return "redirect:/user/registerSuccess";	
		}
		//회원 테이블에 데이터가 존재하면 최초 관리자를 생성할 수 없으므로 실패 페이지로 이동한다.
				return "redirect:/user/setupFailure";
	}
	@RequestMapping(value = "/setup", method = RequestMethod.GET)
	public String setupAdminForm(Member member, Model model) throws Exception {
		// 회원 테이블 데이터 건수를 확인하여 최초 관리자 등록 페이지를 표시한다.
		if (service.countAll() == 0) {
			return "user/setup";
		}
		//회원 테이블에 데이터가 존재하면 최초 관리자를 생성할 수 없으므로 실패 페이지로 이동한다.
		return "user/setupFailure";
	}
}

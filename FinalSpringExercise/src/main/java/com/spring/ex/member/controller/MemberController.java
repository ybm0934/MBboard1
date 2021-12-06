package com.spring.ex.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.ex.member.model.MemberService;
import com.spring.ex.member.model.MemberVO;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String getLogin() {
		return "member/login";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String postLogin(MemberVO memberVo, Model model) {

		return "";
	}

	@RequestMapping(value = "/register.do", method = RequestMethod.GET)
	public String getRegister() {
		return "member/register";
	}

	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public String postRegister(MemberVO memberVo, Model model) {
		return "";
	}

	@RequestMapping("/idCheck.do")
	public String idCheck(String userid, Model model) {
		System.out.println("idCheck 실행");
		System.out.println("파라미터 userid = " + userid);

		int n = memberService.idCheck(userid);

		String result = "";
		if (n > 0) {
			result = "아이디가 존재합니다.";
		} else {
			result = "사용 가능한 아이디입니다.";
		}

		model.addAttribute("result", result);

		return "member/idCheck";
	}

}

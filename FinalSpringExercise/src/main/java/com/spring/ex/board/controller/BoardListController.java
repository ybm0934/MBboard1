package com.spring.ex.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.ex.common.SearchVO;

@Controller
public class BoardListController {
	
	@RequestMapping("boardList.do")
	public String getBoardList(@ModelAttribute SearchVO searchVo, Model model) {
		
		
		
		
		return "board/boardList";
	}

}

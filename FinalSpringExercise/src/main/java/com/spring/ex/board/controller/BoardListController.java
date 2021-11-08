package com.spring.ex.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.ex.board.model.BoardDAO;
import com.spring.ex.board.model.BoardVO;
import com.spring.ex.common.PaginationInfo;
import com.spring.ex.common.SearchVO;

@Controller
public class BoardListController {

	@Autowired
	private BoardDAO boardDao;

	@RequestMapping("/boardList.do")
	public String getBoardList(@ModelAttribute SearchVO searchVo, Model model) {
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		pagingInfo.setPageSize(searchVo.getPageSize());
		pagingInfo.setBlockSize(searchVo.getBlockSize());
		int totalRecord = boardDao.getTotalRecord(searchVo);
		pagingInfo.setTotalRecord(totalRecord);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		searchVo.setLastRecordIndex(pagingInfo.getLastRecordIndex());
		
		List<BoardVO> list = boardDao.getListBoard(searchVo);
		System.out.println(pagingInfo);
		System.out.println(searchVo);

		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		model.addAttribute("searchVo", searchVo);

		return "board/boardList";
	}

}

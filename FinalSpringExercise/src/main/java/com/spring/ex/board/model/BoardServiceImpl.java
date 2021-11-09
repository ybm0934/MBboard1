package com.spring.ex.board.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.ex.common.SearchVO;

public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDao;

	public int insertBoard(BoardVO boardVo) {
		int n = boardDao.insertBoard(boardVo);
		return n;
	}

	public int getTotalRecord(SearchVO searchVo) {
		int n = boardDao.getTotalRecord(searchVo);
		return n;
	}

	public List<BoardVO> getListBoard(SearchVO searchVo) {
		List<BoardVO> list = boardDao.getListBoard(searchVo);
		return list;
	}

	public BoardVO getBoard(int no) {
		BoardVO boardVo = boardDao.getBoard(no);
		return boardVo;
	}

	public int updateBoard(BoardVO boardVo) {
		int n = boardDao.updateBoard(boardVo);
		return n;
	}

	public int deleteBoard(int no) {
		int n = boardDao.deleteBoard(no);
		return n;
	}

}

package com.spring.ex.board.model;

import java.util.List;

import com.spring.ex.common.SearchVO;

public interface BoardService {
	
	public int insertBoard(BoardVO boardVo);
	public int getTotalRecord(SearchVO searchVo);
	public List<BoardVO> getListBoard(SearchVO searchVo);
	public BoardVO getBoard(int no);
	public int updateBoard(BoardVO boardVo);
	public int deleteBoard(int no);

}

package com.spring.ex.board.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.ex.common.SearchVO;

public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	private String namespace = "config.mybatis.mapper.board.";

	public int insertBoard(BoardVO boardVo) {
		int n = sqlSession.insert(namespace + "insertBoard", boardVo);
		return n;
	}

	public int getTotalRecord(SearchVO searchVo) {
		int n = sqlSession.selectOne(namespace + "getTotalRecord", searchVo);
		return n;
	}

	public List<BoardVO> getListBoard(SearchVO searchVo) {
		List<BoardVO> list = sqlSession.selectList(namespace + "getListBoard", searchVo);
		return list;
	}

	public BoardVO getBoard(int no) {
		BoardVO boardVo = sqlSession.selectOne(namespace + "getBoard", no);
		return boardVo;
	}

	public int updateBoard(BoardVO boardVo) {
		int n = sqlSession.update(namespace + "updateBoard", boardVo);
		return n;
	}

	public int deleteBoard(int no) {
		int n = sqlSession.delete(namespace + "deleteBoard", no);
		return n;
	}

}

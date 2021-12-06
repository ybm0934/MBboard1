package com.spring.ex.member.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	private String namespace = "config.mybatis.mapper.member.";

	public int login(MemberVO memberVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int register(MemberVO memberVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int idCheck(String userid) {
		int n = sqlSession.selectOne(namespace + "getUserid", userid);
		return n;
	}

}

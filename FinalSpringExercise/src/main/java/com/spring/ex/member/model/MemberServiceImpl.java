package com.spring.ex.member.model;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDao;

	public int login(MemberVO memberVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int register(MemberVO memberVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int idCheck(String userid) {
		int n = memberDao.idCheck(userid);
		return n;
	}

}

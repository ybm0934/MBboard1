package com.spring.ex.member.model;

public interface MemberDAO {
	
	public int login(MemberVO memberVo);
	public int register(MemberVO memberVo);
	public int idCheck(String userid);

}

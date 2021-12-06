package com.spring.ex.member.model;

public class MemberVO {

	private int no;
	private String userid;
	private String pwd;
	private String pwdOk;
	private String grade;
	private double volume;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwdOk() {
		return pwdOk;
	}

	public void setPwdOk(String pwdOk) {
		this.pwdOk = pwdOk;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "MemberVO [no=" + no + ", userid=" + userid + ", pwd=" + pwd + ", pwdOk=" + pwdOk + ", grade=" + grade
				+ ", volume=" + volume + "]";
	}

}

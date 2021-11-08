package com.spring.ex.board.model;

import java.sql.Timestamp;

public class BoardVO {

	private int no;
	private String name;
	private String pwd;
	private String title;
	private String content;
	private char files;
	private Timestamp regdate;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public char getFiles() {
		return files;
	}

	public void setFiles(char files) {
		this.files = files;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "BoardVO [no=" + no + ", name=" + name + ", pwd=" + pwd + ", title=" + title + ", content=" + content
				+ ", files=" + files + ", regdate=" + regdate + "]";
	}

}

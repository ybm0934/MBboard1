package com.spring.ex.disk.model;

public class DiskVO {

	private String name; // 파일 이름
	private String type; // 파일 타입
	private double size; // 파일 사이즈
	private String sizeType; // 용량 구별
	private String lastMod; // 마지막 수정일
	private String path; // 경로
	private String upPath; // 부모 경로
	private String upName; // 부모 이름

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getSizeType() {
		return sizeType;
	}

	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}

	public String getLastMod() {
		return lastMod;
	}

	public void setLastMod(String lastMod) {
		this.lastMod = lastMod;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUpPath() {
		return upPath;
	}

	public void setUpPath(String upPath) {
		this.upPath = upPath;
	}

	public String getUpName() {
		return upName;
	}

	public void setUpName(String upName) {
		this.upName = upName;
	}

	@Override
	public String toString() {
		return "DiskVO [name=" + name + ", type=" + type + ", size=" + size + ", sizeType=" + sizeType + ", lastMod="
				+ lastMod + ", path=" + path + ", upPath=" + upPath + ", upName=" + upName + "]";
	}

}

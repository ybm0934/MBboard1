package com.spring.ex.disk.model;

public class DiskVO {

	private String name;
	private String type;
	private double size;
	private String lastMod;

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

	public String getLastMod() {
		return lastMod;
	}

	public void setLastMod(String lastMod) {
		this.lastMod = lastMod;
	}

	@Override
	public String toString() {
		return "DiskVO [name=" + name + ", type=" + type + ", size=" + size + ", lastMod=" + lastMod + "]";
	}

}

package com.spring.ex.common;

public class PaginationInfo {

	private int currentPage; // 현재 페이지
	private int pageSize; // 한 페이지에 보여질 레코드 수
	private int blockSize; // 한 블럭에 보여질 페이지 수
	private int totalRecord; // 총 레코드 수
	private int totalPage; // 총 페이지 수
	private int firstPage; // 블럭 당 시작 페이지
	private int lastPage; // 블럭 당 끝 페이지
	private int firstRecordIndex; // 페이지 당 시작 레코드
	private int lastRecordIndex; // 페이지 당 끝 레코드

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public int getTotalPage() {
		totalPage = (int) Math.ceil((float) totalRecord / pageSize);
		return totalPage;
	}

	public int getFirstPage() {
		firstPage = currentPage - ((currentPage - 1) % blockSize);
		return firstPage;
	}

	public int getLastPage() {
		lastPage = getFirstPage() + (blockSize - 1);
		if (lastPage > getTotalPage())
			lastPage = getTotalPage();
		return lastPage;
	}

	public int getFirstRecordIndex() {
		firstRecordIndex = (getCurrentPage() - 1) * getPageSize();
		return firstRecordIndex;
	}

	public int getLastRecordIndex() {
		lastRecordIndex = getCurrentPage() * getPageSize();
		return lastRecordIndex;
	}
	
	@Override
	public String toString() {
		return "PaginationInfo [currentPage=" + currentPage + ", pageSize=" + pageSize + ", blockSize=" + blockSize
				+ ", totalRecord=" + totalRecord + ", totalPage=" + totalPage + ", firstPage=" + firstPage
				+ ", lastPage=" + lastPage + ", firstRecordIndex=" + firstRecordIndex + ", lastRecordIndex="
				+ lastRecordIndex + "]";
	}

}

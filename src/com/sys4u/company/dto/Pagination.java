package com.sys4u.company.dto;

public class Pagination {
	private final int rowsPerPage;
	private final int pagesPerPage;
	private final int totalCount;
	
	private int pageNum;
	private  int startRowIndex;
	private  int endRowIndex;
	private  int startPageIndex;
	private  int endPageIndex;
	private  int totalPageCount;
	
	public Pagination(int rowsPerPage, int pageNum, int pagesPerPage, int totalCount) {
		super();
		this.rowsPerPage = rowsPerPage;
		this.pageNum = pageNum;
		this.pagesPerPage = pagesPerPage;
		this.totalCount = totalCount;
		calculate();
	}

	private void calculate() {
		startRowIndex = rowsPerPage * (pageNum - 1) + 1;
		endRowIndex = rowsPerPage * pageNum;
		
		startPageIndex = ((pageNum -1) / pagesPerPage) * pagesPerPage + 1;
		endPageIndex = startPageIndex + pagesPerPage - 1;
		
		totalPageCount = (int) Math.ceil((double)totalCount / (double)rowsPerPage);
	}
	
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		calculate();
	}

	public int getPagesPerPage() {
		return pagesPerPage;
	}
	
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	
	public int getStartRowIndex() {
		return startRowIndex;
	}

	public int getEndRowIndex() {
		return endRowIndex;
	}

	public int getStartPageIndex() {
		return startPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

}

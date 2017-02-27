package com.zuidaima.redistest.vo;

/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pager implements Serializable {
	private int pageShow = 2;
	private int nowPage = 1;
	private int totalNum = 0;
	private int totalPage = 1;
	private List results = new ArrayList();

	public int getFromNum() {
		return (this.getNowPage() - 1) * this.pageShow;
	}

	public List getResults() {
		return this.results;
	}

	public void setResults(List results) {
		this.results = results;
	}

	public int getPageShow() {
		return this.pageShow;
	}

	public void setPageShow(int pageShow) {
		this.pageShow = pageShow;
	}

	public int getNowPage() {
		if (this.nowPage <= 0) {
			this.nowPage = 1;
		}

		if (this.nowPage > this.getTotalPage()) {
			this.nowPage = this.getTotalPage();
		}

		return this.nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getTotalPage() {
		return this.totalNum > 0 ? (int) Math.ceil((double) this.totalNum * 1.0D / (double) this.pageShow)
				: this.totalPage;
	}

	public String toString() {
		return "Pager [pageShow=" + this.getPageShow() + ", nowPage=" + this.getNowPage() + ", totalNum="
				+ this.getTotalNum() + ", totalPage=" + this.getTotalPage() + ", results=" + this.results + "]";
	}
}

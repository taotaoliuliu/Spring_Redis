package com.zuidaima.redistest.vo;

import java.util.UUID;

public class CommentModel {
	
	private String uuid;
	
	
	

	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	private String articleUuid;
	private String content;
	private String userUuid;
	private int supportcount;
	private int againstcount;
	
	private String userName;
	private String articleTitle;
	private int accept;
	public String getArticleUuid() {
		return articleUuid;
	}
	public void setArticleUuid(String articleUuid) {
		this.articleUuid = articleUuid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	public int getSupportcount() {
		return supportcount;
	}
	public void setSupportcount(int supportcount) {
		this.supportcount = supportcount;
	}
	public int getAgainstcount() {
		return againstcount;
	}
	public void setAgainstcount(int againstcount) {
		this.againstcount = againstcount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public int getAccept() {
		return accept;
	}
	public void setAccept(int accept) {
		this.accept = accept;
	}
	
	
	
	public String genUuid(){
		
		return UUID.randomUUID().toString();
		
	}
	
	
}

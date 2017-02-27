package com.zuidaima.redistest.vo;

import java.util.UUID;

public class ArticleModel {
	
	
	private String uuid;
	
	private String createOpeTime;
	
	private String title;
	private String content;
	private String userUuid;
	private String source;
	private String digest;
	private int lockflag = 1;
	private int checkflag = 1;
	private int viewcount = 0;
	private String tag;
	private int supportcount = 0;
	private int againstcount = 0;
	private int displayflag = 1;
	private int originalflag = 1;
	private int topflag = 1;
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
	public String getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public int getLockflag() {
		return lockflag;
	}
	public void setLockflag(int lockflag) {
		this.lockflag = lockflag;
	}
	public int getCheckflag() {
		return checkflag;
	}
	public void setCheckflag(int checkflag) {
		this.checkflag = checkflag;
	}
	public int getViewcount() {
		return viewcount;
	}
	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
	public int getDisplayflag() {
		return displayflag;
	}
	public void setDisplayflag(int displayflag) {
		this.displayflag = displayflag;
	}
	public int getOriginalflag() {
		return originalflag;
	}
	public void setOriginalflag(int originalflag) {
		this.originalflag = originalflag;
	}
	public int getTopflag() {
		return topflag;
	}
	public void setTopflag(int topflag) {
		this.topflag = topflag;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCreateOpeTime() {
		return createOpeTime;
	}
	public void setCreateOpeTime(String createOpeTime) {
		this.createOpeTime = createOpeTime;
	}
	
	
	
public String genUuid(){
		
		return UUID.randomUUID().toString();
		
	}
	
	
}

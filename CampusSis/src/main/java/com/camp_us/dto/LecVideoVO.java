package com.camp_us.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class LecVideoVO {
    private String lecvidId;
    private String lecId;
    private String lecvidName;
    private String lecvidVidname;
    private String lecvidVidpath;
    private String lecvidThumbnail;
    private String lecvidWeek;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lecvidDeadline;
    private String lecvidDetail;
    private int progress; // 학생 시청용
    private String status; // 출석, 지각, 결석
	public String getLecvidId() {
		return lecvidId;
	}
	public void setLecvidId(String lecvidId) {
		this.lecvidId = lecvidId;
	}
	public String getLecId() {
		return lecId;
	}
	public void setLecId(String lecId) {
		this.lecId = lecId;
	}
	public String getLecvidName() {
		return lecvidName;
	}
	public void setLecvidName(String lecvidName) {
		this.lecvidName = lecvidName;
	}
	public String getLecvidVidname() {
		return lecvidVidname;
	}
	public void setLecvidVidname(String lecvidVidname) {
		this.lecvidVidname = lecvidVidname;
	}
	public String getLecvidVidpath() {
		return lecvidVidpath;
	}
	public void setLecvidVidpath(String lecvidVidpath) {
		this.lecvidVidpath = lecvidVidpath;
	}
	public String getLecvidThumbnail() {
		return lecvidThumbnail;
	}
	public void setLecvidThumbnail(String lecvidThumbnail) {
		this.lecvidThumbnail = lecvidThumbnail;
	}
	public String getLecvidWeek() {
		return lecvidWeek;
	}
	public void setLecvidWeek(String lecvidWeek) {
		this.lecvidWeek = lecvidWeek;
	}
	public Date getLecvidDeadline() {
		return lecvidDeadline;
	}
	public void setLecvidDeadline(Date lecvidDeadline) {
		this.lecvidDeadline = lecvidDeadline;
	}
	public String getLecvidDetail() {
		return lecvidDetail;
	}
	public void setLecvidDetail(String lecvidDetail) {
		this.lecvidDetail = lecvidDetail;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}


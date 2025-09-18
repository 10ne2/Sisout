package com.camp_us.dto;

import java.util.Date;

public class LectureVO {
    private String lec_id;
    private String lec_name;
    private String lec_date;
    private String lec_desc;
    private String lec_point;
    private String lec_evalway;
    private String lec_profes;
    private String lec_filename;
    private String lec_filepath;  // VARCHAR2 로 저장
    private Date regdate;
	public String getLec_id() {
		return lec_id;
	}
	public void setLec_id(String lec_id) {
		this.lec_id = lec_id;
	}
	public String getLec_name() {
		return lec_name;
	}
	public void setLec_name(String lec_name) {
		this.lec_name = lec_name;
	}
	public String getLec_date() {
		return lec_date;
	}
	public void setLec_date(String lec_date) {
		this.lec_date = lec_date;
	}
	public String getLec_desc() {
		return lec_desc;
	}
	public void setLec_desc(String lec_desc) {
		this.lec_desc = lec_desc;
	}
	public String getLec_point() {
		return lec_point;
	}
	public void setLec_point(String lec_point) {
		this.lec_point = lec_point;
	}
	public String getLec_evalway() {
		return lec_evalway;
	}
	public void setLec_evalway(String lec_evalway) {
		this.lec_evalway = lec_evalway;
	}
	public String getLec_profes() {
		return lec_profes;
	}
	public void setLec_profes(String lec_profes) {
		this.lec_profes = lec_profes;
	}
	public String getLec_filename() {
		return lec_filename;
	}
	public void setLec_filename(String lec_filename) {
		this.lec_filename = lec_filename;
	}
	public String getLec_filepath() {
		return lec_filepath;
	}
	public void setLec_filepath(String lec_filepath) {
		this.lec_filepath = lec_filepath;
	}
	public Date getRegDate() {
		return regdate;
	}
	public void setRegDate(Date regDate) {
		this.regdate = regDate;
	}
}

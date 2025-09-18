package com.camp_us.dto;

import java.util.Date;

public class StuLecVO {
    private String stu_id;      // 학생 ID
    private String lec_id;      // 강의 ID
    private String lec_name;    // 강의명
    private Date lec_date;      // 강의 날짜 (또는 시작일)
    private String lec_desc;    // 강의 설명
    private String lec_point;  // 학점 등
    private String lec_evalway; // 평가 방법
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
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
	public Date getLec_date() {
		return lec_date;
	}
	public void setLec_date(Date lec_date) {
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


}
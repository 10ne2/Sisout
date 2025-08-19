package com.camp_us.dto;

import java.util.Date;

public class LecClassVO {
	private String lec_id;
	private String lec_name;
	private Date lec_date;
	private String lec_desc;
	private String point;
	private String lec_evalway;
	private String profes;
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
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getLec_evalway() {
		return lec_evalway;
	}
	public void setLec_evalway(String lec_evalway) {
		this.lec_evalway = lec_evalway;
	}
	public String getProfes() {
		return profes;
	}
	public void setProfes(String profes) {
		this.profes = profes;
	}
	
	
}

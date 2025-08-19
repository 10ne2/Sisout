package com.camp_us.dto;

import java.util.Date;

public class UnsubmitHomeworkVO {
	
	private String hw_name; // 과제명
	private Date hw_enddate; // 마감일
	private String hw_enddateStr;
	private String lec_name; // 강의명
	private int d_day; // d-day
	private String mem_name; // 교수명
	private String stu_id;
	private String mem_id;
	
	public String getHw_name() {
		return hw_name;
	}
	public void setHw_name(String hw_name) {
		this.hw_name = hw_name;
	}
	public Date getHw_enddate() {
		return hw_enddate;
	}
	public void setHw_enddate(Date hw_enddate) {
		this.hw_enddate = hw_enddate;
	}
	public String getHw_enddateStr() {
		return hw_enddateStr;
	}
	public void setHw_enddateStr(String hw_enddateStr) {
		this.hw_enddateStr = hw_enddateStr;
	}
	public String getLec_name() {
		return lec_name;
	}
	public void setLec_name(String lec_name) {
		this.lec_name = lec_name;
	}
	public int getD_day() {
		return d_day;
	}
	public void setD_day(int d_day) {
		this.d_day = d_day;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	
	
}

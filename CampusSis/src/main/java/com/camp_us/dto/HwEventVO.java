package com.camp_us.dto;

import java.util.Date;

public class HwEventVO {
    private Long hw_no;
    private String title;
    private String type;
    private Date hw_enddate;
    private String lec_id;
	public Long getHw_no() {
		return hw_no;
	}
	public void setHw_no(Long hw_no) {
		this.hw_no = hw_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getHw_enddate() {
		return hw_enddate;
	}
	public void setHw_enddate(Date hw_enddate) {
		this.hw_enddate = hw_enddate;
	}
	public String getLec_id() {
		return lec_id;
	}
	public void setLec_id(String lec_id) {
		this.lec_id = lec_id;
	}
    
    
}
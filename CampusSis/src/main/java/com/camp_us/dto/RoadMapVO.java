package com.camp_us.dto;

import java.util.Date;
import java.util.List;

import com.camp_us.dto.AttachVO;

public class RoadMapVO {
	private String rm_id;
	private String project_id;
	private String team_id;
	private String rm_name;
	private String rm_category;
	private Date rm_regdate;
	private String rm_content;
	private String rm_status;
	private Date rm_update;
	private String eval_id;
	private String eval_status; 
	private String writer;
	private Date rm_endate;
	private Date rm_stdate;
	private String project_name;
	private String mem_name;
	private List<AttachVO> attachList;
	
	
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public Date getRm_endate() {
		return rm_endate;
	}
	public void setRm_endate(Date rm_endate) {
		this.rm_endate = rm_endate;
	}
	public Date getRm_stdate() {
		return rm_stdate;
	}
	public void setRm_stdate(Date rm_stdate) {
		this.rm_stdate = rm_stdate;
	}
	public String getEval_id() {
		return eval_id;
	}
	public void setEval_id(String eval_id) {
		this.eval_id = eval_id;
	}
	public String getEval_status() {
		return eval_status;
	}
	public void setEval_status(String eval_status) {
		this.eval_status = eval_status;
	}
	public String getRm_id() {
		return rm_id;
	}
	public void setRm_id(String rm_id) {
		this.rm_id = rm_id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getRm_name() {
		return rm_name;
	}
	public void setRm_name(String rm_name) {
		this.rm_name = rm_name;
	}
	public String getRm_category() {
		return rm_category;
	}
	public void setRm_category(String rm_category) {
		this.rm_category = rm_category;
	}
	public Date getRm_regdate() {
		return rm_regdate;
	}
	public void setRm_regdate(Date rm_regdate) {
		this.rm_regdate = rm_regdate;
	}
	public String getRm_content() {
		return rm_content;
	}
	public void setRm_content(String rm_content) {
		this.rm_content = rm_content;
	}
	public String getRm_status() {
		return rm_status;
	}
	public void setRm_status(String rm_status) {
		this.rm_status = rm_status;
	}
	public Date getRm_update() {
		return rm_update;
	}
	public void setRm_update(Date rm_update) {
		this.rm_update = rm_update;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public List<AttachVO> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachVO> attachList) {
		this.attachList = attachList;
	}
	
	
	
}

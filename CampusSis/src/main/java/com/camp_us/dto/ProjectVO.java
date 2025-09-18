package com.camp_us.dto;

import java.util.Date;

public class ProjectVO {
	private String project_id; //프로젝트 아이디
	private String project_name; //프로젝트 이름
	private String project_desc; //프로젝트 상세설명
	private String project_proc; //프로젝트 진행정도
	private Date project_stdate; //일정 시작일
	private Date project_endate; //일정 종료일
	private String pjsumit_eval; //최종 프로젝트 평가 여부
	private String profes_id; // 교수아이디 (교수의 아이디와 fk)
	private Date reg_date = new Date(); //생성일
	private Date update_date; //수정일
	private String team_id; //팀 아이디 (팀 테이블과 fk)
	private String samester;
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProject_desc() {
		return project_desc;
	}
	public void setProject_desc(String project_desc) {
		this.project_desc = project_desc;
	}
	public String getProject_proc() {
		return project_proc;
	}
	public void setProject_proc(String project_proc) {
		this.project_proc = project_proc;
	}
	public Date getProject_stdate() {
		return project_stdate;
	}
	public void setProject_stdate(Date project_stdate) {
		this.project_stdate = project_stdate;
	}
	public Date getProject_endate() {
		return project_endate;
	}
	public void setProject_endate(Date project_endate) {
		this.project_endate = project_endate;
	}
	public String getPjsumit_eval() {
		return pjsumit_eval;
	}
	public void setPjsumit_eval(String pjsumit_eval) {
		this.pjsumit_eval = pjsumit_eval;
	}
	public String getProfes_id() {
		return profes_id;
	}
	public void setProfes_id(String profes_id) {
		this.profes_id = profes_id;
	}

	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getSamester() {
		return samester;
	}
	public void setSamester(String samester) {
		this.samester = samester;
	}
	
	
}
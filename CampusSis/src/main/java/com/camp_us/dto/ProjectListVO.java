package com.camp_us.dto;

import java.util.Date;
import java.util.List;

public class ProjectListVO {
	private String project_id;
	private String project_name;	
	private Date project_stdate;
	private Date project_endate; 
	private String profes_id;
	private String profes_name;
	private Date reg_date;
	private String update_date;
	private String team_id;
	private String samester;
	private String team_leader;
	private String leader_name;
	private String team_member;
	private String rm_status;
	private String eval_status;
	private String mem_id;
	private List<String> mem_name;
	private String project_desc;
	public String getProfes_name() {
		return profes_name;
	}
	public void setProfes_name(String profes_name) {
		this.profes_name = profes_name;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getLeader_name() {
		return leader_name;
	}
	public void setLeader_name(String leader_name) {
		this.leader_name = leader_name;
	}
	public List<String> getMem_name() {
		return mem_name;
	}
	public void setMem_name(List<String> mem_name) {
		this.mem_name = mem_name;
	}
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
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
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
	public String getTeam_leader() {
		return team_leader;
	}
	public void setTeam_leader(String team_leader) {
		this.team_leader = team_leader;
	}
	public String getTeam_member() {
		return team_member;
	}
	public void setTeam_member(String team_member) {
		this.team_member = team_member;
	}
	public String getRm_status() {
		return rm_status;
	}
	public void setRm_status(String rm_status) {
		this.rm_status = rm_status;
	}
	public String getEval_status() {
		return eval_status;
	}
	public void setEval_status(String eval_status) {
		this.eval_status = eval_status;
	}
	public String getProject_desc() {
		return project_desc;
	}
	public void setProject_desc(String project_desc) {
		this.project_desc = project_desc;
	}
	
	
}

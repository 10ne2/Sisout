package com.camp_us.dto;

import java.util.Date;

public class EditBfProjectVO {
	private String before_id;
	private String project_id;
	private String team_id;
	private String project_name;
	private Date project_stdate;
	private Date project_endate;
	private String team_member_ids;
	private String team_member_names;
	private String samester;
	private String team_leader;
	private String leader_name;
	private String edit_content;
	
	public String getBefore_id() {
		return before_id;
	}
	public void setBefore_id(String before_id) {
		this.before_id = before_id;
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
	public String getSamester() {
		return samester;
	}
	public void setSamester(String samester) {
		this.samester = samester;
	}
	public String getLeader_name() {
		return leader_name;
	}
	public void setLeader_name(String leader_name) {
		this.leader_name = leader_name;
	}
	public String getTeam_member_ids() {
		return team_member_ids;
	}
	public void setTeam_member_ids(String team_member_ids) {
		this.team_member_ids = team_member_ids;
	}
	public String getTeam_member_names() {
		return team_member_names;
	}
	public void setTeam_member_names(String team_member_names) {
		this.team_member_names = team_member_names;
	}
	public String getTeam_leader() {
		return team_leader;
	}
	public void setTeam_leader(String team_leader) {
		this.team_leader = team_leader;
	}
	public String getEdit_content() {
		return edit_content;
	}
	public void setEdit_content(String edit_content) {
		this.edit_content = edit_content;
	}

	
	
}

package com.camp_us.command;

import java.util.List;

import com.camp_us.dto.ProjectListVO;
import com.camp_us.dto.TeamVO;

public class ModifyProjectRequest {
    private ProjectListVO project;
    private TeamVO team;
    private List<String> team_member_ids;
    private String before_id;
	public ProjectListVO getProject() {
		return project;
	}
	public void setProject(ProjectListVO project) {
		this.project = project;
	}
	public TeamVO getTeam() {
		return team;
	}
	public void setTeam(TeamVO team) {
		this.team = team;
	}
	public List<String> getTeam_member_ids() {
		return team_member_ids;
	}
	public void setTeam_member_ids(List<String> team_member_ids) {
		this.team_member_ids = team_member_ids;
	}
	public String getBefore_id() {
		return before_id;
	}
	public void setBefore_id(String before_id) {
		this.before_id = before_id;
	}
    
}
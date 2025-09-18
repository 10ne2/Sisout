package com.camp_us.dto;

public class TeamVO {
	private String team_id;     //team_member 테이블과 fk 
	private String team_leader; //팀장명
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getTeam_leader() {
		return team_leader;
	}
	public void setTeam_leader(String team_leader) {
		this.team_leader = team_leader;
	}
	
	
}

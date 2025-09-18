package com.camp_us.dto;

public class TeamMemberVO {
	private String team_id;  //팀 고유번호
	private String team_member;  //학생 아이디들과 fk
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getTeam_member() {
		return team_member;
	}
	public void setTeam_member(String team_member) {
		this.team_member = team_member;
	}
	
}

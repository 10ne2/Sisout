package com.camp_us.command;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.camp_us.dto.ProjectVO;
import com.camp_us.dto.TeamMemberVO;
import com.camp_us.dto.TeamVO;

public class ProjectRegistCommand {
	
	private String project_id;
    private String project_name;
    private String profes_id;
    private String team_member;
    private String team_leader;
    private String samester;
    private String project_desc;
    private Date project_stdate;
    private Date project_endate;
    
    

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

	public String getProject_desc() {
		return project_desc;
	}

	public void setProject_desc(String project_desc) {
		this.project_desc = project_desc;
	}

	public String getSamester() {
		return samester;
	}

	public void setSamester(String samester) {
		this.samester = samester;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	private List<String> stu_id_list;

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProfes_id() {
        return profes_id;
    }

    public void setProfes_id(String profes_id) {
        this.profes_id = profes_id;
    }

    public String getTeam_leader() {
        return team_leader;
    }

    public void setTeam_leader(String team_leader) {
        this.team_leader = team_leader;
    }

    public List<String> getStu_id_list() {
        return stu_id_list;
    }

    public void setStu_id_list(List<String> stu_id_list) {
        this.stu_id_list = stu_id_list;
    }

    // ProjectVO로 변환
    public ProjectVO toProjectVO(String project_id, String team_id) {
        ProjectVO project = new ProjectVO();
        project.setProject_id(project_id);
        project.setProject_name(project_name);
        project.setProfes_id(profes_id);
        project.setTeam_id(team_id); 
        project.setSamester(samester);
        project.setProject_desc(project_desc);
        project.setProject_stdate(project_stdate);
        project.setProject_endate(project_endate);
        return project;
    }

    // TeamVO로 변환
    public TeamVO toTeamVO(String team_id) {
        TeamVO team = new TeamVO();
        team.setTeam_id(team_id);
        team.setTeam_leader(this.team_leader);
        return team;
    }

    // TeamMemberVO 리스트 생성
    public List<TeamMemberVO> toTeamMemberVOList(String team_id) {
        return stu_id_list.stream()  // 리스트 이름은 그대로 써도 무방 (나중에 바꿔도 됨)
            .map(stuId -> {
                TeamMemberVO member = new TeamMemberVO();
                member.setTeam_id(team_id);
                member.setTeam_member(stuId);  // team_member 필드 사용
                return member;
            })
            .collect(Collectors.toList());
    }

	public String getTeam_member() {
		return team_member;
	}

	public void setTeam_member(String mem_id) {
		this.team_member = mem_id;
	}
    
}
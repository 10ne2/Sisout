package com.camp_us.command;

import java.util.List;
import java.util.stream.Collectors;

import com.camp_us.dto.EditBfProjectVO;
import com.camp_us.dto.ProjectVO;
import com.camp_us.dto.TeamMemberVO;
import com.camp_us.dto.TeamVO;

public class ProjectModifyCommand extends ProjectRegistCommand {
    
    private String before_id;          // 수정전 번호
    private String team_id;            // 팀 아이디
    private String team_member_ids;    // 팀원 아이디들 (콤마 구분)
    private String team_member_names;  // 팀원 이름들 (콤마 구분)
    private String team_leader;          // 팀 리더 아이디
    private String leader_name;        // 팀 리더 이름
    private String edit_content;
    public String getBefore_id() {
        return before_id;
    }

    public void setBefore_id(String before_id) {
        this.before_id = before_id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
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

	public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    
    public String getEdit_content() {
		return edit_content;
	}

	public void setEdit_content(String edit_content) {
		this.edit_content = edit_content;
	}

	// ProjectVO 변환
    public ProjectVO toProjectVO() {
        ProjectVO project = new ProjectVO();
        project.setProject_id(getProject_id());
        project.setProject_name(getProject_name());
        project.setProfes_id(getProfes_id());
        project.setTeam_id(team_id);
        project.setSamester(getSamester());
        project.setProject_desc(getProject_desc());
        project.setProject_stdate(getProject_stdate());
        project.setProject_endate(getProject_endate());
        return project;
    }

    // TeamVO 변환
    public TeamVO toTeamVO() {
        TeamVO team = new TeamVO();
        team.setTeam_id(team_id);
        team.setTeam_leader(team_leader);
        return team;
    }

    // TeamMemberVO 리스트 변환
    public List<TeamMemberVO> toTeamMemberVOList() {
        return List.of(team_member_ids.split(","))
            .stream()
            .map(id -> {
                TeamMemberVO vo = new TeamMemberVO();
                vo.setTeam_id(team_id);
                vo.setTeam_member(id.trim());
                return vo;
            })
            .collect(Collectors.toList());
    }
    public EditBfProjectVO toEditBfProjectVO() {
        EditBfProjectVO editVO = new EditBfProjectVO();
        editVO.setBefore_id(this.getBefore_id());
        editVO.setProject_id(this.getProject_id());
        editVO.setTeam_id(this.getTeam_id());
        editVO.setProject_name(this.getProject_name());
        editVO.setProject_stdate(this.getProject_stdate());
        editVO.setProject_endate(this.getProject_endate());
        editVO.setTeam_member_ids(this.getTeam_member_ids()); // String: "id1,id2,id3"
        editVO.setTeam_member_names(this.getTeam_member_names()); // String: "name1,name2,name3"
        editVO.setSamester(this.getSamester());
        editVO.setTeam_leader(this.getTeam_leader());
        editVO.setLeader_name(this.getLeader_name());
        editVO.setEdit_content(this.getEdit_content());
        return editVO;
    }
}
package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camp_us.command.PageMakerStu;
import com.camp_us.command.PageMakerPro;
import com.camp_us.dao.ProjectDAO;
import com.camp_us.dto.EditBfProjectVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.ProjectListVO;
import com.camp_us.dto.ProjectVO;
import com.camp_us.dto.TeamMemberVO;
import com.camp_us.dto.TeamVO;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectDAO projectDAO;

    @Autowired
    public ProjectServiceImpl(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public List<ProjectVO> selectProjectList(String mem_id) throws SQLException {
        return projectDAO.selectProjectList(mem_id);
    }

    @Override
    public List<MemberVO> selectTeamMemberList() throws SQLException {
        return projectDAO.selectTeamMemberList();
    }

    @Override
    public List<MemberVO> selectProfessorList() throws SQLException {
        return projectDAO.selectProfessorList();
    }

    @Override
    public MemberVO selectMemberListById(String mem_id) throws SQLException {
        return projectDAO.selectMemberListById(mem_id);
    }

    @Override
    public void insertTeamMemberList(TeamMemberVO teamMember) throws SQLException {
        projectDAO.insertTeamMemberList(teamMember);
    }

    @Override
    public void insertProject(ProjectVO project) throws SQLException {
        projectDAO.insertProject(project);
    }

    @Override
    public void insertTeamLeader(TeamVO team) throws SQLException {
        projectDAO.insertTeamLeader(team);
    }

    @Override
    public String selectProjectSeqNext() throws SQLException {
        return projectDAO.selectProjectSeqNext();
    }

    @Override
    public String selectTeamSeqNext() throws SQLException {
        return projectDAO.selectTeamSeqNext();
    }

	@Override
	public List<ProjectListVO> searchProjectList(PageMakerStu pageMaker, String mem_id) throws SQLException {
		List<ProjectListVO> projectlist = projectDAO.selectsearchProjectList(pageMaker, mem_id);
		
		int totalCount = projectDAO.selectsearchProjectListCount(pageMaker, mem_id);
		pageMaker.setTotalCount(totalCount);
		return projectlist;
	}

	@Override
	public List<ProjectListVO> searchProjectListpro(PageMakerPro pageMaker, String mem_id) throws SQLException {
		List<ProjectListVO> projectlist = projectDAO.selectsearchProjectListpro(pageMaker, mem_id);
		
		int totalCount = projectDAO.selectsearchProjectListCountpro(pageMaker, mem_id);
		pageMaker.setTotalCount(totalCount);
		
		return projectlist;
	}

	@Override
	public List<String>selectTeamProfessor(String project_id) throws SQLException {
		return projectDAO.selectTeamProfessor(project_id);
	}

	@Override
	public List<String> selectTeamMembers(String project_id) throws SQLException {
		return projectDAO.selectTeamMembers(project_id);
	}

	@Override
	public  List<ProjectListVO>selectTeamLeader(String project_id) throws SQLException {
		return projectDAO.selectTeamleader(project_id);
	}

	@Override
	public List<EditBfProjectVO> insertEditBeforeProject(EditBfProjectVO editBfProjectVO) throws SQLException {
			return projectDAO.insertEditBeforeProject(editBfProjectVO);
	}

	@Override
	public List<ProjectListVO> selectProjectByProjectId(String project_id) throws SQLException {
		return projectDAO.selectProjectByProjectId(project_id);
	}

	@Override
	public String selectEditBeforeSeqNext() throws SQLException {
		return projectDAO.selectEditBeforeSeqNext();
	}

	@Override
	public List<String> selectEditStatusByProjectid(String project_id) throws SQLException {
		return projectDAO.selectEditStatusByProjectid(project_id);
	}

	@Override
	public List<EditBfProjectVO> selectEditProjectByProjectId(String project_id) throws SQLException {
		return projectDAO.selectEditProjectByProjectId(project_id);
	}

	@Override
	public void updateProjectTeamAndMembers(ProjectListVO project, TeamVO team, List<TeamMemberVO> teamMember)
			throws SQLException {
		 projectDAO.deleteByTeamMemberId(team.getTeam_id());

		    for (TeamMemberVO member : teamMember) {
		        member.setTeam_id(team.getTeam_id());
		        projectDAO.insertTeamMember(member);
		    }

		    projectDAO.updateTeamLeader(team);
		    projectDAO.updateProject(project);
		
		
	}

	@Override
	public void deleteEditBefore(String before_id) throws SQLException {
	 projectDAO.deleteEditBefore(before_id);		
	}

	@Override
	public List<ProjectListVO> selectModifyRequestProjectList(PageMakerPro pageMaker, String mem_id)
			throws SQLException {
		List<ProjectListVO> projectlist = projectDAO.selectModifyRequestProjectList(pageMaker, mem_id);
		
		int totalCount = projectDAO.selectModifyRequestProjectListCount(pageMaker, mem_id);
		pageMaker.setTotalCount(totalCount);
		return projectlist;
	}

	@Override
	public void deleteTeamByTeamId(String team_id) throws SQLException {
		projectDAO.deleteTeamByTeamId(team_id);
		
	}
	
	
	
}
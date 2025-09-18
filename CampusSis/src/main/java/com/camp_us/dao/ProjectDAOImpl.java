package com.camp_us.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.camp_us.command.PageMakerStu;
import com.camp_us.command.PageMakerPro;
import com.camp_us.dto.EditBfProjectVO;
import com.camp_us.dto.MemberVO;
import com.camp_us.dto.ProjectListVO;
import com.camp_us.dto.ProjectVO;
import com.camp_us.dto.TeamMemberVO;
import com.camp_us.dto.TeamVO;

public class ProjectDAOImpl implements ProjectDAO {

    private SqlSession session;
     
    public ProjectDAOImpl(SqlSession session) {
		this.session = session;
	}
   	@Override
	public List<MemberVO> selectTeamMemberList() throws SQLException {
		List<MemberVO> teamMemberList = session.selectList("Project-Mapper.selectTeamMemberList");
		return teamMemberList;
	}

	@Override
	public List<MemberVO> selectProfessorList() throws SQLException {
		List<MemberVO> professorList = session.selectList("Project-Mapper.selectProfessorList");
		return professorList;
	}

	@Override
	public MemberVO selectMemberListById(String mem_id) throws SQLException {
		MemberVO member = session.selectOne("Project-Mapper.selectMemberListById",mem_id);
		return member;
	}

	@Override
	public void insertTeamMemberList(TeamMemberVO teamMember) throws SQLException {
		session.insert("Project-Mapper.insertTeamMemberList",teamMember);
		
	}

	@Override
	public void insertProject(ProjectVO project) throws SQLException {
		session.insert("Project-Mapper.insertProject",project);
		
	}

	@Override
	public void insertTeamLeader(TeamVO team) throws SQLException {
		session.insert("Project-Mapper.insertTeamLeader",team);
		
	}

	@Override
	public String selectProjectSeqNext() throws SQLException {
		String project_id = session.selectOne("Project-Mapper.selectProjectSeqNext");
		return project_id;
	}

	@Override
	public String selectTeamSeqNext() throws SQLException {
		String team_id = session.selectOne("Project-Mapper.selectTeamSeqNext");
		return team_id;
	}
	
	@Override
	public List<ProjectVO> selectProjectList(String mem_id) throws SQLException {
		List<ProjectVO> projectList = session.selectList("Project-Mapper.selectProjectList",mem_id);
		return projectList;
	}

	@Override
	public List<ProjectListVO> selectsearchProjectList(PageMakerStu pageMaker, String mem_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("searchType",pageMaker.getSearchType());
		dataMap.put("project_name",pageMaker.getProject_name());
		dataMap.put("mem_id", mem_id);
		dataMap.put("project_stdate", pageMaker.getProject_stdate());
		dataMap.put("project_endate", pageMaker.getProject_endate());
		List<ProjectListVO> projectlist = session.selectList("Project-Mapper.selectsearchProjectList",dataMap,bounds);
		
		return projectlist;
	}

	@Override
	public int selectsearchProjectListCount(PageMakerStu pageMaker, String mem_id) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("searchType",pageMaker.getSearchType());
		dataMap.put("project_name",pageMaker.getProject_name());
		dataMap.put("mem_id", mem_id);
		dataMap.put("project_stdate", pageMaker.getProject_stdate());
		dataMap.put("project_endate", pageMaker.getProject_endate());
		int count = session.selectOne("Project-Mapper.selectsearchProjectListCount",dataMap);
		return count;
	}

	@Override
	public List<ProjectListVO> selectsearchProjectListpro(PageMakerPro pageMaker, String mem_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("searchType", pageMaker.getSearchType());
		dataMap.put("project_name",pageMaker.getProject_name());
		dataMap.put("mem_id", mem_id);
		dataMap.put("project_stdate", pageMaker.getProject_stdate());
		dataMap.put("project_endate", pageMaker.getProject_endate());
		List<ProjectListVO> projectlist = session.selectList("Project-Mapper.selectsearchProjectListpro",dataMap,bounds);
		
		return projectlist;
	}

	@Override
	public int selectsearchProjectListCountpro(PageMakerPro pageMaker, String mem_id) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("searchType", pageMaker.getSearchType());
		dataMap.put("project_name",pageMaker.getProject_name());
		dataMap.put("project_stdate", pageMaker.getProject_stdate());
		dataMap.put("project_endate", pageMaker.getProject_endate());
		dataMap.put("mem_id", mem_id);
		
		int count = session.selectOne("Project-Mapper.selectsearchProjectListCountpro",dataMap);
		return count;
	}

	@Override
	public List<String> selectTeamMembers(String project_id) throws SQLException {
		return session.selectList("Project-Mapper.selectTeamMembers",project_id);
	}

	@Override
	public List<ProjectListVO> selectTeamleader(String project_id) throws SQLException {
		return session.selectList("Project-Mapper.selectsearchTeamleader",project_id);
	}

	@Override
	public List<String> selectTeamProfessor(String project_id) throws SQLException {
		return session.selectList("Project-Mapper.selectTeamProfessor",project_id);
	}

	@Override
	public List<ProjectListVO> selectProjectByProjectId(String project_id) throws SQLException {
		return session.selectList("Project-Mapper.selectProjectByProjectId",project_id);
	}

	@Override
	public List<EditBfProjectVO> insertEditBeforeProject(EditBfProjectVO editBfProject) throws SQLException {
		return session.selectList("Project-Mapper.insertEditBeforeProject",editBfProject);
	}

	@Override
	public String selectEditBeforeSeqNext() throws SQLException {
		String before_id = session.selectOne("Project-Mapper.selectEditBeforeSeqNext");
		return before_id;
	}

	@Override
	public List<String> selectEditStatusByProjectid(String project_id) throws SQLException {
		return session.selectList("Project-Mapper.selectEditStatusByProjectid",project_id);
	}

	@Override
	public List<EditBfProjectVO> selectEditProjectByProjectId(String project_id) throws SQLException {
		return session.selectList("Project-Mapper.selectEditProjectByProjectId",project_id);
	}

	@Override
	public void deleteByTeamMemberId(String team_id) throws SQLException {
		session.delete("Project-Mapper.deleteByTeamMemberId",team_id);
		
	}

	@Override
	public void updateTeamLeader(TeamVO team) throws SQLException {
		session.update("Project-Mapper.updateTeamLeader",team);
		
	}

	@Override
	public void insertTeamMember(TeamMemberVO teamMember) throws SQLException {
		session.insert("Project-Mapper.insertTeamMember",teamMember);
		
	}

	@Override
	public void updateProject(ProjectListVO projectList) throws SQLException {
		session.update("Project-Mapper.updateProject",projectList);
		
	}

	@Override
	public void deleteEditBefore(String before_id) throws SQLException {
		session.delete("Project-Mapper.deleteEditBefore",before_id);
	}

	@Override
	public List<ProjectListVO> selectModifyRequestProjectList(PageMakerPro pageMaker, String mem_id)
			throws SQLException {
		int offset = pageMaker.getStartRow()-1;
			int limit = pageMaker.getPerPageNum();
			RowBounds bounds = new RowBounds(offset,limit);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("keyword", pageMaker.getKeyword());
			dataMap.put("searchType", pageMaker.getSearchType());
			dataMap.put("project_name",pageMaker.getProject_name());
			dataMap.put("mem_id", mem_id);
			dataMap.put("project_stdate", pageMaker.getProject_stdate());
			dataMap.put("project_endate", pageMaker.getProject_endate());
			List<ProjectListVO> projectlist = session.selectList("Project-Mapper.selectModifyRequestProjectList",dataMap,bounds);
			
			return projectlist;
	}

	@Override
	public int selectModifyRequestProjectListCount(PageMakerPro pageMaker, String mem_id) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("searchType", pageMaker.getSearchType());
		dataMap.put("project_name",pageMaker.getProject_name());
		dataMap.put("project_stdate", pageMaker.getProject_stdate());
		dataMap.put("project_endate", pageMaker.getProject_endate());
		dataMap.put("mem_id", mem_id);
		
		int count = session.selectOne("Project-Mapper.selectModifyRequestProjectListCount",dataMap);
		return count;
	}

	@Override
	public void deleteTeamByTeamId(String team_id) throws SQLException {
		session.delete("Project-Mapper.deleteTeamByTeamId",team_id);
		
	}
	
    
}
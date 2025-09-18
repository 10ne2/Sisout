package com.camp_us.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.camp_us.command.PageMakerPro;
import com.camp_us.command.PageMakerRM;
import com.camp_us.dto.EvaluationVO;
import com.camp_us.dto.ProjectListVO;
import com.camp_us.dto.RoadMapVO;

public class RoadMapDAOImpl implements RoadMapDAO{
	
	private SqlSession session;

    public RoadMapDAOImpl(SqlSession session) {
        this.session = session;
    }
    
	@Override
	public List<ProjectListVO> selectsearchProjectListstu(PageMakerPro pageMaker, String mem_id) throws SQLException {
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
		dataMap.put("eval_status",pageMaker.getEval_status());
		List<ProjectListVO> projectList = session.selectList("RoadMap-Mapper.selectsearchProjectListstu",dataMap,bounds);
		
		return projectList;
	}

	@Override
	public int selectsearchProjectListCountstu(PageMakerPro pageMaker, String mem_id) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("searchType", pageMaker.getSearchType());
		dataMap.put("project_name",pageMaker.getProject_name());
		dataMap.put("project_stdate", pageMaker.getProject_stdate());
		dataMap.put("project_endate", pageMaker.getProject_endate());
		dataMap.put("mem_id", mem_id);
		dataMap.put("rm_status",pageMaker.getRm_status());
		dataMap.put("eval_status",pageMaker.getEval_status());
		int count = session.selectOne("RoadMap-Mapper.selectsearchProjectListCountstu",dataMap);
		return count;
	}

	@Override
	public List<RoadMapVO> selectRoadMapByProject_id(PageMakerRM pageMaker, String project_id) throws SQLException {
		int offset = pageMaker.getStartRow()-1;
		int limit = pageMaker.getPerPageNum();
		RowBounds bounds = new RowBounds(offset,limit);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("searchType", pageMaker.getSearchType());
		dataMap.put("rm_name",pageMaker.getRm_name());
		dataMap.put("rm_category", pageMaker.getRm_category());
		dataMap.put("rm_stdate", pageMaker.getRm_stdate());
		dataMap.put("rm_endate", pageMaker.getRm_endate());
		dataMap.put("project_id", project_id);
		dataMap.put("eval_status", pageMaker.getEval_status());
		List<RoadMapVO> roadMapList = session.selectList("RoadMap-Mapper.selectRoadMapByProject_id",dataMap,bounds);
		return roadMapList;
	}

	@Override
	public void insertRoadMap(RoadMapVO roadMap) throws SQLException {
		session.insert("RoadMap-Mapper.insertRoadMap", roadMap);
		
	}

	@Override
	public void deleteRoadMap(String rm_id) throws SQLException {
		session.delete("RoadMap-Mapper.deleteRoadMap", rm_id);
		
	}

	@Override
	public String selectRoadMapSeqNext() throws SQLException {
		String rm_id = session.selectOne("RoadMap-Mapper.selectRoadMapSeqNext");
		return rm_id;
	}

	@Override
	public int selectRoadMapByProject_idCount(PageMakerRM pageMaker, String project_id) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("keyword", pageMaker.getKeyword());
		dataMap.put("searchType", pageMaker.getSearchType());
		dataMap.put("rm_name",pageMaker.getRm_name());
		dataMap.put("rm_category", pageMaker.getRm_category());
		dataMap.put("rm_stdate", pageMaker.getRm_stdate());
		dataMap.put("rm_endate", pageMaker.getRm_endate());
		dataMap.put("project_id", project_id);
		dataMap.put("eval_status", pageMaker.getEval_status());
		
		
		int count = session.selectOne("RoadMap-Mapper.selectRoadMapByProject_idCount",dataMap);
		return count;
	}

	@Override
	public RoadMapVO selectRoadMapByRm_id(String rm_id) throws SQLException {
		RoadMapVO roadMap = session.selectOne("RoadMap-Mapper.selectRoadMapByRm_id",rm_id);
		return roadMap;
	}

	@Override
	public List<String> selectEvalStatusByRMid(String rm_id) throws SQLException {
		return session.selectList("RoadMap-Mapper.selectEvalStatusByRMid",rm_id);
	}

	@Override
	public void updateEvalStatus(String rm_id) throws SQLException {
		session.update("updateEvalStatus",rm_id);
	}

	@Override
	public void updateRoadMapStatus(String project_id) throws SQLException {
		session.update("updateRoadMapStatus",project_id);
		
	}
}

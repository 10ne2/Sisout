package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMakerPro;
import com.camp_us.command.PageMakerRM;
import com.camp_us.dto.EvaluationVO;
import com.camp_us.dto.ProjectListVO;
import com.camp_us.dto.RoadMapVO;

public interface RoadMapDAO {
	List<ProjectListVO>selectsearchProjectListstu(PageMakerPro pageMaker, String mem_id) throws SQLException;
	
	int selectsearchProjectListCountstu(PageMakerPro pageMaker, String mem_id);
	
	List<RoadMapVO>selectRoadMapByProject_id(PageMakerRM pageMaker, String project_id) throws SQLException;
	
	int selectRoadMapByProject_idCount(PageMakerRM pageMaker,String project_id)throws SQLException;
	
	void insertRoadMap(RoadMapVO roadMap)throws SQLException;
	
	void deleteRoadMap(String rm_id)throws SQLException;
	
	String selectRoadMapSeqNext()throws SQLException;
	
	RoadMapVO selectRoadMapByRm_id(String rm_id)throws SQLException;
	
	List<String> selectEvalStatusByRMid(String rm_id)throws SQLException;
	
	void updateEvalStatus(String rm_id)throws SQLException;
	
	void updateRoadMapStatus(String project_id)throws SQLException;
}

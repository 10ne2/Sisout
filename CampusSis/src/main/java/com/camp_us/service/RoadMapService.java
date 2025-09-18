package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.command.PageMakerPro;
import com.camp_us.command.PageMakerRM;
import com.camp_us.dto.EvaluationVO;
import com.camp_us.dto.ProjectListVO;
import com.camp_us.dto.RoadMapVO;

public interface RoadMapService {
	List<ProjectListVO> projectlist(PageMakerPro pageMaker,String mem_id) throws SQLException;

	List<RoadMapVO>roadmaplist(PageMakerRM pageMaker,String project_id)throws SQLException;
	
	List<String>evalStatus(String rm_id)throws SQLException;
	
	RoadMapVO detail(String rm_id)throws SQLException;
	
	void regist(RoadMapVO roadMap)throws SQLException;
	
	void remove(String rm_id)throws SQLException;
	
	void updateEvalStatus(String rm_id)throws SQLException;
	
	void updateRoadMapStatus(String rm_id)throws SQLException;
}

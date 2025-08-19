package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.UnsubmitHomeworkVO;

public interface UnsubmitHomeworkDAO {
	
	List<UnsubmitHomeworkVO> selectUnsubmitHomework(String stu_id)throws SQLException;
	
	UnsubmitHomeworkVO selectStuIdbyMemId(String mem_id)throws SQLException;
}

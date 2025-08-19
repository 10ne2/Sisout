package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.UnsubmitHomeworkVO;

public interface UnsubmitHomeworkService {
	List<UnsubmitHomeworkVO> getUnsubmitHomeworkList(String stu_id) throws SQLException;
	
	UnsubmitHomeworkVO getStuIdbyMemId(String mem_id)throws SQLException;
}

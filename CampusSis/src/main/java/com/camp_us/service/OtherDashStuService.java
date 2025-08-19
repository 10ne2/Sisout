package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.OtherDashStuVO;

public interface OtherDashStuService {
	List<OtherDashStuVO> getNoticeList(String stu_id) throws SQLException;
	
	List<OtherDashStuVO> getFileList(String stu_id) throws SQLException;
	
	List<OtherDashStuVO> getAttendenceList(String stu_id) throws SQLException;
	
	List<OtherDashStuVO> getAttendencePercent(String stu_id) throws SQLException;
}

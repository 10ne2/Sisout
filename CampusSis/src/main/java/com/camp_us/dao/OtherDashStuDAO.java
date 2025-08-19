package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;

import com.camp_us.dto.OtherDashStuVO;

public interface OtherDashStuDAO {
	
	List<OtherDashStuVO> selectNoticeList(String stu_id)throws SQLException;
	
	List<OtherDashStuVO> selectFileList(String stu_id)throws SQLException;
	
	List<OtherDashStuVO> selectAttendenceList(String stu_id)throws SQLException;
	
	List<OtherDashStuVO> selectAttendencePercent(String stu_id)throws SQLException;
}

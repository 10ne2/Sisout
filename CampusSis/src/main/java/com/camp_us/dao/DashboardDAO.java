package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.camp_us.dto.HwEventVO;

public interface DashboardDAO {
	List<HwEventVO> selectUpcomingHwEventsByStudent(String memId) throws SQLException;
	List<Map<String,Object>> selectHwDotsByStudent(String memId) throws SQLException;
	List<HwEventVO> selectUpcomingHwEventsByProfessor(String memId) throws SQLException;
	List<Map<String,Object>> selectHwDotsByProfessor(String memId) throws SQLException;
}
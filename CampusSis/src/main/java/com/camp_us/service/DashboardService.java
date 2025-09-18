package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.camp_us.dto.HwEventVO;

public interface DashboardService {
    // 학생 대시보드 (memId 기준)
    List<HwEventVO> getUpcomingHwEvents(String memId) throws SQLException;
    List<Map<String,Object>> getHwDots(String memId) throws SQLException;

    // 교수 대시보드 (memId = 교수의 회원ID)
    List<HwEventVO> getUpcomingHwEventsForProfessor(String memId) throws SQLException;
    List<Map<String,Object>> getHwDotsForProfessor(String memId) throws SQLException;
}
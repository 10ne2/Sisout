package com.camp_us.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.camp_us.dao.DashboardDAO;
import com.camp_us.dto.HwEventVO;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardDAO dashboardDAO;

    public DashboardServiceImpl(DashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    @Override
    public List<HwEventVO> getUpcomingHwEvents(String memId) throws SQLException {
        return dashboardDAO.selectUpcomingHwEventsByStudent(memId);
    }
    @Override
    public List<Map<String,Object>> getHwDots(String memId) throws SQLException {
        return dashboardDAO.selectHwDotsByStudent(memId);
    }
    @Override
    public List<HwEventVO> getUpcomingHwEventsForProfessor(String memId) throws SQLException {
        return dashboardDAO.selectUpcomingHwEventsByProfessor(memId);
    }
    @Override
    public List<Map<String,Object>> getHwDotsForProfessor(String memId) throws SQLException {
        return dashboardDAO.selectHwDotsByProfessor(memId);
    }
}
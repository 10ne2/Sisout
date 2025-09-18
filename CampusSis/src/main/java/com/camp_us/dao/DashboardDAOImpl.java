package com.camp_us.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.camp_us.dto.HwEventVO;

@Repository("dashboardDAO")
public class DashboardDAOImpl implements DashboardDAO {

    private static final String NAMESPACE = "HomeworkDashboard-Mapper";

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<HwEventVO> selectUpcomingHwEventsByStudent(String memId) throws SQLException {
        // <select id="selectUpcomingHwEventsByStudent" parameterType="string" .../>
        return sqlSession.selectList(NAMESPACE + ".selectUpcomingHwEventsByStudent", memId);
    }

    @Override
    public List<Map<String, Object>> selectHwDotsByStudent(String memId) throws SQLException {
        // <select id="selectHwDotsByStudent" parameterType="string" resultType="map"/>
        return sqlSession.selectList(NAMESPACE + ".selectHwDotsByStudent", memId);
    }

    @Override
    public List<HwEventVO> selectUpcomingHwEventsByProfessor(String memId) throws SQLException {
        // <select id="selectUpcomingHwEventsByProfessor" parameterType="string" .../>
        return sqlSession.selectList(NAMESPACE + ".selectUpcomingHwEventsByProfessor", memId);
    }

    @Override
    public List<Map<String, Object>> selectHwDotsByProfessor(String memId) throws SQLException {
        // <select id="selectHwDotsByProfessor" parameterType="string" resultType="map"/>
        return sqlSession.selectList(NAMESPACE + ".selectHwDotsByProfessor", memId);
    }
}
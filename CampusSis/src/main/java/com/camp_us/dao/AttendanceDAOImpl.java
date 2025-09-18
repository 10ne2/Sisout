package com.camp_us.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.AttendanceVO;

public class AttendanceDAOImpl implements AttendanceDAO {
    private SqlSession sqlSession;

    private final String namespace = "AttendanceMapper.";

    public AttendanceDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void insertAttendance(AttendanceVO vo) {
        sqlSession.insert(namespace + "insertAttendance", vo);
    }
    
    @Override
    public void updateProgressAndDetail(String aNo, int newProgress) {
        Map<String, Object> params = new HashMap<>();
        params.put("aNo", aNo);
        params.put("newProgress", newProgress);
        sqlSession.update("AttendanceMapper.updateProgressAndDetail", params);
    }
    
    @Override
    public void updateAttendance(AttendanceVO vo) {
        sqlSession.update(namespace + "updateAttendance", vo);
    }

	@Override
	public AttendanceVO selectAttendance(String aNo) {
		return sqlSession.selectOne(namespace + "selectAttendance", aNo);
	}


}

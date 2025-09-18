package com.camp_us.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.camp_us.dto.LectureListVO;

public class LectureListDAOImpl implements LectureListDAO {
    private SqlSession sqlSession;
    private final String namespace = "LectureListMapper.";

    public LectureListDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<String> getStudentMemIdsByLecId(String lecId) {
        return sqlSession.selectList(namespace + "getStudentMemIdsByLecId", lecId);
    }

    @Override
    public String getLecsIdByLecIdAndMemId(String lecId, String memId) {
        java.util.Map<String, String> paramMap = new java.util.HashMap<>();
        paramMap.put("lecId", lecId);
        paramMap.put("memId", memId);
        return sqlSession.selectOne(namespace + "getLecsIdByLecIdAndMemId", paramMap);
    }
    
    @Override
    public List<LectureListVO> getStudentsByLecture(String lecId) {
        return sqlSession.selectList("LectureListMapper.getStudentsByLecture", lecId);
    }
}